package com.onetwo.commentservice.adapter.in.web.comment.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onetwo.commentservice.adapter.in.web.comment.request.RegisterCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.request.UpdateCommentRequest;
import com.onetwo.commentservice.adapter.in.web.config.TestHeader;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;
import com.onetwo.commentservice.application.port.in.usecase.RegisterCommentUseCase;
import com.onetwo.commentservice.common.GlobalStatus;
import com.onetwo.commentservice.common.GlobalUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(TestHeader.class)
class CommentControllerBootTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RegisterCommentUseCase registerCommentUseCase;

    @Autowired
    private TestHeader testHeader;

    private final Integer category = 1;
    private final Long targetId = 1L;
    private final String userId = "testUserId";
    private final String content = "content";

    @Test
    @Transactional
    @DisplayName("[통합][Web Adapter] Comment 등록 - 성공 테스트")
    void postCommentSuccessTest() throws Exception {
        //given
        RegisterCommentRequest registerCommentRequest = new RegisterCommentRequest(category, targetId, content);

        //when
        ResultActions resultActions = mockMvc.perform(
                post(GlobalUrl.COMMENT_ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerCommentRequest))
                        .headers(testHeader.getRequestHeaderWithMockAccessKey(userId))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("register-comment",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key"),
                                        headerWithName(GlobalStatus.ACCESS_TOKEN).description("유저의 access-token")
                                ),
                                requestFields(
                                        fieldWithPath("category").type(JsonFieldType.NUMBER).description("등록할 comment의 목표 category"),
                                        fieldWithPath("targetId").type(JsonFieldType.NUMBER).description("등록할 comment의 목표 target id"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("등록할 comment 본문")
                                ),
                                responseFields(
                                        fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("등록 성공시 comment id"),
                                        fieldWithPath("isRegisterSuccess").type(JsonFieldType.BOOLEAN).description("등록 완료 여부")
                                )
                        )
                );
    }

    @Test
    @Transactional
    @DisplayName("[통합][Web Adapter] Comment 삭제 - 성공 테스트")
    void deleteCommentSuccessTest() throws Exception {
        //given
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content);
        RegisterCommentResponseDto registerCommentResponseDto = registerCommentUseCase.registerComment(registerCommentCommand);

        Long commentId = registerCommentResponseDto.commentId();

        //when
        ResultActions resultActions = mockMvc.perform(
                delete(GlobalUrl.COMMENT_ROOT + GlobalUrl.PATH_VARIABLE_COMMENT_ID_WITH_BRACE, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(testHeader.getRequestHeaderWithMockAccessKey(userId))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("delete-comment",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key"),
                                        headerWithName(GlobalStatus.ACCESS_TOKEN).description("유저의 access-token")
                                ),
                                pathParameters(
                                        parameterWithName(GlobalUrl.PATH_VARIABLE_COMMENT_ID).description("삭제할 comment id")
                                ),
                                responseFields(
                                        fieldWithPath("isDeleteSuccess").type(JsonFieldType.BOOLEAN).description("삭제 성공 여부")
                                )
                        )
                );
    }

    @Test
    @Transactional
    @DisplayName("[통합][Web Adapter] Comment 수정 - 성공 테스트")
    void updateCommentSuccessTest() throws Exception {
        //given
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest(content);

        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content);
        RegisterCommentResponseDto registerCommentResponseDto = registerCommentUseCase.registerComment(registerCommentCommand);

        Long commentId = registerCommentResponseDto.commentId();

        //when
        ResultActions resultActions = mockMvc.perform(
                put(GlobalUrl.COMMENT_ROOT + GlobalUrl.PATH_VARIABLE_COMMENT_ID_WITH_BRACE, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCommentRequest))
                        .headers(testHeader.getRequestHeaderWithMockAccessKey(userId))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-comment",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key"),
                                        headerWithName(GlobalStatus.ACCESS_TOKEN).description("유저의 access-token")
                                ),
                                pathParameters(
                                        parameterWithName(GlobalUrl.PATH_VARIABLE_COMMENT_ID).description("삭제할 comment id")
                                ),
                                requestFields(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 comment 본문")
                                ),
                                responseFields(
                                        fieldWithPath("isUpdateSuccess").type(JsonFieldType.BOOLEAN).description("삭제 성공 여부")
                                )
                        )
                );
    }

    @Test
    @Transactional
    @DisplayName("[통합][Web Adapter] Comment 상세 조회 - 성공 테스트")
    void findCommentDetailsSuccessTest() throws Exception {
        //given
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content);
        RegisterCommentResponseDto registerCommentResponseDto = registerCommentUseCase.registerComment(registerCommentCommand);

        Long commentId = registerCommentResponseDto.commentId();

        //when
        ResultActions resultActions = mockMvc.perform(
                get(GlobalUrl.COMMENT_ROOT + GlobalUrl.PATH_VARIABLE_COMMENT_ID_WITH_BRACE, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(testHeader.getRequestHeaderWithMockAccessKey(userId))
                        .accept(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("find-comments-detail",
                                requestHeaders(
                                        headerWithName(GlobalStatus.ACCESS_ID).description("서버 Access id"),
                                        headerWithName(GlobalStatus.ACCESS_KEY).description("서버 Access key"),
                                        headerWithName(GlobalStatus.ACCESS_TOKEN).description("유저의 access-token")
                                ),
                                pathParameters(
                                        parameterWithName(GlobalUrl.PATH_VARIABLE_COMMENT_ID).description("조회할 comment id")
                                ),
                                responseFields(
                                        fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("comment id"),
                                        fieldWithPath("category").type(JsonFieldType.NUMBER).description("comment가 달린 target category"),
                                        fieldWithPath("targetId").type(JsonFieldType.NUMBER).description("comment가 달린 target id"),
                                        fieldWithPath("userId").type(JsonFieldType.STRING).description("작성자 user id"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("comment 본문"),
                                        fieldWithPath("createdDate").type(JsonFieldType.STRING).description("작성 날짜 및 시간")
                                )
                        )
                );
    }
}