package com.onetwo.commentservice.adapter.in.web.comment.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.commentservice.adapter.in.web.comment.mapper.CommentDtoMapper;
import com.onetwo.commentservice.adapter.in.web.comment.request.RegisterCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.request.UpdateCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.response.*;
import com.onetwo.commentservice.adapter.in.web.config.TestConfig;
import com.onetwo.commentservice.application.port.in.command.*;
import com.onetwo.commentservice.application.port.in.response.*;
import com.onetwo.commentservice.application.port.in.usecase.DeleteCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.ReadCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.RegisterCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.UpdateCommentUseCase;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                        SecurityConfig.class
                })
        })
@Import(TestConfig.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterCommentUseCase registerCommentUseCase;

    @MockBean
    private DeleteCommentUseCase deleteCommentUseCase;

    @MockBean
    private UpdateCommentUseCase updateCommentUseCase;

    @MockBean
    private ReadCommentUseCase readCommentUseCase;

    @MockBean
    private CommentDtoMapper commentDtoMapper;

    private final Integer category = 1;
    private final Long targetId = 1L;
    private final Long commentId = 1L;
    private final String userId = "testUserId";
    private final String content = "content";
    private final Instant createdDate = Instant.now();

    @Test
    @WithMockUser(username = userId)
    @DisplayName("[단위][Web Adapter] Comment 등록 - 성공 테스트")
    void postCommentSuccessTest() throws Exception {
        //given
        RegisterCommentRequest registerCommentRequest = new RegisterCommentRequest(category, targetId, content);
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content);
        RegisterCommentResponseDto registerCommentResponseDto = new RegisterCommentResponseDto(commentId, true);
        RegisterCommentResponse registerCommentResponse = new RegisterCommentResponse(commentId, true);

        when(commentDtoMapper.registerRequestToCommand(anyString(), any(RegisterCommentRequest.class))).thenReturn(registerCommentCommand);
        when(registerCommentUseCase.registerComment(any(RegisterCommentCommand.class))).thenReturn(registerCommentResponseDto);
        when(commentDtoMapper.dtoToRegisterResponse(any(RegisterCommentResponseDto.class))).thenReturn(registerCommentResponse);
        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.COMMENT_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerCommentRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = userId)
    @DisplayName("[단위][Web Adapter] Comment 삭제 - 성공 테스트")
    void deleteCommentSuccessTest() throws Exception {
        //given
        DeleteCommentCommand deleteCommentCommand = new DeleteCommentCommand(commentId, userId);
        DeleteCommentResponseDto deleteCommentResponseDto = new DeleteCommentResponseDto(true);
        DeleteCommentResponse deletePostingCommand = new DeleteCommentResponse(true);

        when(commentDtoMapper.deleteRequestToCommand(anyLong(), anyString())).thenReturn(deleteCommentCommand);
        when(deleteCommentUseCase.deleteComment(any(DeleteCommentCommand.class))).thenReturn(deleteCommentResponseDto);
        when(commentDtoMapper.dtoToDeleteResponse(any(DeleteCommentResponseDto.class))).thenReturn(deletePostingCommand);
        //when
        ResultActions resultActions = mockMvc.perform(
                delete(GlobalUrl.COMMENT_ROOT + GlobalUrl.PATH_VARIABLE_COMMENT_ID_WITH_BRACE, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = userId)
    @DisplayName("[단위][Web Adapter] Comment 수정 - 성공 테스트")
    void updateCommentSuccessTest() throws Exception {
        //given
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest(content);
        UpdateCommentCommand updateCommentCommand = new UpdateCommentCommand(commentId, userId, content);
        UpdateCommentResponseDto updateCommentResponseDto = new UpdateCommentResponseDto(true);
        UpdateCommentResponse updateCommentResponse = new UpdateCommentResponse(true);

        when(commentDtoMapper.updateRequestCommand(anyLong(), anyString(), any(UpdateCommentRequest.class))).thenReturn(updateCommentCommand);
        when(updateCommentUseCase.updateComment(any(UpdateCommentCommand.class))).thenReturn(updateCommentResponseDto);
        when(commentDtoMapper.dtoToUpdateResponse(any(UpdateCommentResponseDto.class))).thenReturn(updateCommentResponse);
        //when
        ResultActions resultActions = mockMvc.perform(
                put(GlobalUrl.COMMENT_ROOT + GlobalUrl.PATH_VARIABLE_COMMENT_ID_WITH_BRACE, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCommentRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("[단위][Web Adapter] Comment 상세 조회 - 성공 테스트")
    void findCommentDetailSuccessTest() throws Exception {
        //given
        FindCommentDetailCommand findCommentDetailCommand = new FindCommentDetailCommand(commentId);
        CommentDetailResponseDto commentDetailResponseDto = new CommentDetailResponseDto(commentId, category, targetId, userId, content, createdDate);
        CommentDetailResponse commentDetailResponse = new CommentDetailResponse(commentId, category, targetId, userId, content, createdDate);

        when(commentDtoMapper.findRequestToCommand(anyLong())).thenReturn(findCommentDetailCommand);
        when(readCommentUseCase.findCommentsDetail(any(FindCommentDetailCommand.class))).thenReturn(commentDetailResponseDto);
        when(commentDtoMapper.dtoToDetailResponse(any(CommentDetailResponseDto.class))).thenReturn(commentDetailResponse);
        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.COMMENT_ROOT + GlobalUrl.PATH_VARIABLE_COMMENT_ID_WITH_BRACE, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("[단위][Web Adapter] Comment 갯수 조회 - 성공 테스트")
    void countCommentSuccessTest() throws Exception {
        //given
        CountCommentCommand countCommentCommand = new CountCommentCommand(category, targetId);
        CountCommentResponseDto countCommentResponseDto = new CountCommentResponseDto(1016);
        CommentCountResponse countCommentResponse = new CommentCountResponse(countCommentResponseDto.commentCount());

        when(commentDtoMapper.countRequestToCommand(anyInt(), anyLong())).thenReturn(countCommentCommand);
        when(readCommentUseCase.getCommentCount(any(CountCommentCommand.class))).thenReturn(countCommentResponseDto);
        when(commentDtoMapper.dtoToCountResponse(any(CountCommentResponseDto.class))).thenReturn(countCommentResponse);
        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.COMMENT_COUNT + GlobalUrl.PATH_VARIABLE_CATEGORY_WITH_BRACE + GlobalUrl.PATH_VARIABLE_TARGET_ID_WITH_BRACE
                        , category, targetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }
}