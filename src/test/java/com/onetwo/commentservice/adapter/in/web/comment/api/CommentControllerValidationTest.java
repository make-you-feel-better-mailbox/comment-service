package com.onetwo.commentservice.adapter.in.web.comment.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.commentservice.adapter.in.web.comment.mapper.CommentDtoMapper;
import com.onetwo.commentservice.adapter.in.web.comment.request.RegisterCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.request.UpdateCommentRequest;
import com.onetwo.commentservice.adapter.in.web.config.TestConfig;
import com.onetwo.commentservice.application.port.in.usecase.DeleteCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.ReadCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.RegisterCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.UpdateCommentUseCase;
import com.onetwo.commentservice.common.GlobalUrl;
import com.onetwo.commentservice.common.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
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
class CommentControllerValidationTest {

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

    @ParameterizedTest
    @NullSource
    @WithMockUser(username = userId)
    @DisplayName("[단위][Web Adapter] Comment 등록 category validation fail - 실패 테스트")
    void postCommentCategoryValidationFailTest(Integer testCategory) throws Exception {
        //given
        RegisterCommentRequest registerCommentRequest = new RegisterCommentRequest(testCategory, targetId, content);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.COMMENT_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerCommentRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullSource
    @WithMockUser(username = userId)
    @DisplayName("[단위][Web Adapter] Comment 등록 targetId validation fail - 실패 테스트")
    void postCommentTargetIdValidationFailTest(Long testTargetId) throws Exception {
        //given
        RegisterCommentRequest registerCommentRequest = new RegisterCommentRequest(category, testTargetId, content);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.COMMENT_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerCommentRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @WithMockUser(username = userId)
    @DisplayName("[단위][Web Adapter] Comment 등록 content validation fail - 실패 테스트")
    void postCommentContentValidationFailTest(String testContent) throws Exception {
        //given
        RegisterCommentRequest registerCommentRequest = new RegisterCommentRequest(category, targetId, testContent);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.COMMENT_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerCommentRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = userId)
    @DisplayName("[단위][Web Adapter] Comment 삭제 posting id validation fail - 실패 테스트")
    void deleteCommentContentValidationFailTest() throws Exception {
        //given
        String commentId = "badCommentId";

        //when
        ResultActions resultActions = mockMvc.perform(
                delete(GlobalUrl.COMMENT_ROOT + GlobalUrl.PATH_VARIABLE_COMMENT_ID_WITH_BRACE, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = userId)
    @DisplayName("[단위][Web Adapter] Comment 수정 comment id validation fail - 실패 테스트")
    void updateCommentContentCommentIdValidationFailTest() throws Exception {
        //given
        String commentId = "badCommentId";

        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest(content);

        //when
        ResultActions resultActions = mockMvc.perform(
                put(GlobalUrl.COMMENT_ROOT + GlobalUrl.PATH_VARIABLE_COMMENT_ID_WITH_BRACE, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCommentRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @WithMockUser(username = userId)
    @DisplayName("[단위][Web Adapter] Comment 수정 content validation fail - 실패 테스트")
    void updateCommentContentValidationFailTest(String testContent) throws Exception {
        //given
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest(testContent);

        //when
        ResultActions resultActions = mockMvc.perform(
                put(GlobalUrl.COMMENT_ROOT + GlobalUrl.PATH_VARIABLE_COMMENT_ID_WITH_BRACE, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCommentRequest))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("[단위][Web Adapter] Comment 상세 조회 comment id validation fail - 실패 테스트")
    void findCommentDetailsContentCommentIdValidationFailTest() throws Exception {
        //given
        String commentId = "badCommentId";

        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.COMMENT_ROOT + GlobalUrl.PATH_VARIABLE_COMMENT_ID_WITH_BRACE, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andDo(print());
    }
}