package com.onetwo.commentservice.adapter.in.web.comment.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.commentservice.adapter.in.web.comment.mapper.CommentFilterDtoMapper;
import com.onetwo.commentservice.adapter.in.web.comment.request.FilterSliceRequest;
import com.onetwo.commentservice.adapter.in.web.comment.response.FilteredCommentResponse;
import com.onetwo.commentservice.adapter.in.web.config.TestConfig;
import com.onetwo.commentservice.application.port.in.command.CommentFilterCommand;
import com.onetwo.commentservice.application.port.in.response.FilteredCommentResponseDto;
import com.onetwo.commentservice.application.port.in.usecase.ReadCommentUseCase;
import com.onetwo.commentservice.common.GlobalUrl;
import com.onetwo.commentservice.common.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentFilterController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        SecurityConfig.class
                })
        })
@Import(TestConfig.class)
class CommentFilterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReadCommentUseCase readCommentUseCase;

    @MockBean
    private CommentFilterDtoMapper commentFilterDtoMapper;

    private final long postingId = 1L;
    private final String postingIdPath = "postingId";
    private final String content = "content";
    private final String contentPath = "content";
    private final String pageNumber = "pageNumber";
    private final String pageSize = "pageSize";
    private final String userId = "testUserId";
    private final String userIdQueryStringPath = "userId";
    private final Instant filterStartDate = Instant.parse("2000-01-01T00:00:00Z");
    private final Instant filterEndDate = Instant.parse("4000-01-01T00:00:00Z");
    private final String filterStartDatePath = "filterStartDate";
    private final String filterEndDatePath = "filterEndDate";
    private final PageRequest pageRequest = PageRequest.of(0, 20);

    @Test
    @DisplayName("[단위][Web Adapter] Comment Filter by user 조회 성공 - 성공 테스트")
    void getFilteredCommentSuccessTest() throws Exception {
        //given
        CommentFilterCommand commentFilterCommand = new CommentFilterCommand(postingId, userId, content, filterStartDate, filterEndDate, pageRequest);

        List<FilteredCommentResponseDto> filteredCommentResponseDtoList = new ArrayList<>();

        for (int i = 1; i <= pageRequest.getPageSize(); i++) {
            FilteredCommentResponseDto testFilteredComment = new FilteredCommentResponseDto(i, postingId, userId, content + i, Instant.now());
            filteredCommentResponseDtoList.add(testFilteredComment);
        }

        Slice<FilteredCommentResponseDto> filteredPostingResponseDtoSlice = new SliceImpl<>(filteredCommentResponseDtoList, pageRequest, true);

        List<FilteredCommentResponse> filteredCommentResponseList = filteredCommentResponseDtoList.stream()
                .map(responseDto -> new FilteredCommentResponse(
                        responseDto.commentId(),
                        responseDto.postingId(),
                        responseDto.userId(),
                        responseDto.content(),
                        responseDto.createdDate()
                )).toList();

        Slice<FilteredCommentResponse> filteredCommentResponseSlice = new SliceImpl<>(filteredCommentResponseList, pageRequest, true);

        when(commentFilterDtoMapper.filterRequestToCommand(any(FilterSliceRequest.class))).thenReturn(commentFilterCommand);
        when(readCommentUseCase.filterComment(any(CommentFilterCommand.class))).thenReturn(filteredPostingResponseDtoSlice);
        when(commentFilterDtoMapper.dtoToFilteredCommentResponse(any(Slice.class))).thenReturn(filteredCommentResponseSlice);

        String queryString = UriComponentsBuilder.newInstance()
                .queryParam(pageNumber, pageRequest.getPageNumber())
                .queryParam(pageSize, pageRequest.getPageSize())
                .queryParam(postingIdPath, postingId)
                .queryParam(userIdQueryStringPath, userId)
                .queryParam(filterStartDatePath, filterStartDate)
                .queryParam(filterEndDatePath, filterEndDate)
                .queryParam(contentPath, content)
                .build()
                .encode()
                .toUriString();
        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.COMMENT_FILTER + queryString)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }
}