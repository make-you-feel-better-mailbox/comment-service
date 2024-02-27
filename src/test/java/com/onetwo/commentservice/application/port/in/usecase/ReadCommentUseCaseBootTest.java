package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.CommentFilterCommand;
import com.onetwo.commentservice.application.port.in.command.FindCommentDetailCommand;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.CommentDetailResponseDto;
import com.onetwo.commentservice.application.port.in.response.FilteredCommentResponseDto;
import com.onetwo.commentservice.application.port.out.RegisterCommentPort;
import com.onetwo.commentservice.application.service.service.CommentService;
import com.onetwo.commentservice.domain.Comment;
import onetwo.mailboxcommonconfig.common.exceptions.BadRequestException;
import onetwo.mailboxcommonconfig.common.exceptions.NotFoundResourceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@SpringBootTest
@Transactional
class ReadCommentUseCaseBootTest {

    @Autowired
    private CommentService readCommentUseCase;

    @Autowired
    private RegisterCommentPort registerCommentPort;

    private final Long commentId = 1L;
    private final Integer category = 1;
    private final Long targetId = 1L;
    private final String userId = "testUserId";
    private final String content = "content";
    private final Instant filterStartDate = Instant.parse("2000-01-01T00:00:00Z");
    private final Instant filterEndDate = Instant.parse("4000-01-01T00:00:00Z");
    private final PageRequest pageRequest = PageRequest.of(0, 20);

    @Test
    @DisplayName("[단위][Use Case] Comment 상세 조회 - 성공 테스트")
    void readCommentUseCaseSuccessTest() {
        //given
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        Comment savedComment = registerCommentPort.registerComment(comment);

        FindCommentDetailCommand findCommentDetailCommand = new FindCommentDetailCommand(savedComment.getId());

        //when
        CommentDetailResponseDto result = readCommentUseCase.findCommentsDetail(findCommentDetailCommand);

        //then
        Assertions.assertNotNull(result);
    }

    @Test
    @DisplayName("[단위][Use Case] Comment 상세 조회 comment does not exist - 실패 테스트")
    void readCommentUseCaseCommentDoesNotExistFailTest() {
        //given
        FindCommentDetailCommand findCommentDetailCommand = new FindCommentDetailCommand(commentId);

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> readCommentUseCase.findCommentsDetail(findCommentDetailCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] Comment 상세 조회 comment already deleted - 실패 테스트")
    void readCommentUseCaseCommentAlreadyDeletedFailTest() {
        //given
        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        comment.deleteComment();

        Comment savedComment = registerCommentPort.registerComment(comment);

        FindCommentDetailCommand findCommentDetailCommand = new FindCommentDetailCommand(savedComment.getId());

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> readCommentUseCase.findCommentsDetail(findCommentDetailCommand));
    }

    @Test
    @DisplayName("[통합][Use Case] Comment Filter - 성공 테스트")
    void commentFilterUseCaseSuccessTest() {
        //given
        for (int i = 1; i <= pageRequest.getPageSize() + 1; i++) {
            RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content + i);
            Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);
            registerCommentPort.registerComment(comment);
        }

        CommentFilterCommand findCommentDetailCommand = new CommentFilterCommand(category, targetId, userId, content, filterStartDate, filterEndDate, pageRequest);

        //when
        Slice<FilteredCommentResponseDto> result = readCommentUseCase.filterComment(findCommentDetailCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getContent());
        Assertions.assertFalse(result.getContent().isEmpty());
        Assertions.assertTrue(result.hasNext());
    }

    @Test
    @DisplayName("[통합][Use Case] Comment Filter Null case - 성공 테스트")
    void commentFilterUseCaseNullCaseSuccessTest() {
        //given
        for (int i = 1; i <= pageRequest.getPageSize() + 1; i++) {
            RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId + i, category, targetId, content + i);
            Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);
            registerCommentPort.registerComment(comment);
        }

        CommentFilterCommand findCommentDetailCommand = new CommentFilterCommand(category, targetId, null, null, null, null, pageRequest);

        //when
        Slice<FilteredCommentResponseDto> result = readCommentUseCase.filterComment(findCommentDetailCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getContent());
        Assertions.assertFalse(result.getContent().isEmpty());
        Assertions.assertTrue(result.hasNext());
    }
}