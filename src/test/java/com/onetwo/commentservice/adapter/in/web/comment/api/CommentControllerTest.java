package com.onetwo.commentservice.adapter.in.web.comment.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.commentservice.adapter.in.web.comment.mapper.CommentDtoMapper;
import com.onetwo.commentservice.adapter.in.web.comment.request.RegisterCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.response.RegisterCommentResponse;
import com.onetwo.commentservice.adapter.in.web.config.TestConfig;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;
import com.onetwo.commentservice.application.port.in.usecase.RegisterCommentUseCase;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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
    private CommentDtoMapper commentDtoMapper;

    private final Long postingId = 1L;
    private final Long commentId = 1L;
    private final String userId = "testUserId";
    private final String content = "content";

    @Test
    @WithMockUser(username = userId)
    @DisplayName("[단위][Web Adapter] Comment 등록 - 성공 테스트")
    void postCommentSuccessTest() throws Exception {
        //given
        RegisterCommentRequest registerCommentRequest = new RegisterCommentRequest(postingId, content);
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, postingId, content);
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
}