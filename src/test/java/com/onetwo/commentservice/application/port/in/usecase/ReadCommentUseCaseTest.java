package com.onetwo.commentservice.application.port.in.usecase;

import com.onetwo.commentservice.application.port.in.command.CommentFilterCommand;
import com.onetwo.commentservice.application.port.in.command.FindCommentDetailCommand;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.CommentDetailResponseDto;
import com.onetwo.commentservice.application.port.in.response.FilteredCommentResponseDto;
import com.onetwo.commentservice.application.port.out.ReadCommentPort;
import com.onetwo.commentservice.application.port.out.UpdateCommentPort;
import com.onetwo.commentservice.application.service.converter.CommentUseCaseConverter;
import com.onetwo.commentservice.application.service.service.CommentService;
import com.onetwo.commentservice.domain.Comment;
import onetwo.mailboxcommonconfig.common.exceptions.BadRequestException;
import onetwo.mailboxcommonconfig.common.exceptions.NotFoundResourceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReadCommentUseCaseTest {

    @InjectMocks
    private CommentService readCommentUseCase;

    @Mock
    private ReadCommentPort readCommentPort;

    @Mock
    private UpdateCommentPort updateCommentPort;

    @Mock
    private CommentUseCaseConverter commentUseCaseConverter;

    private final long commentId = 1L;
    private final Integer category = 1;
    private final Long targetId = 1L;
    private final String userId = "testUserId";
    private final String content = "content";
    private final Instant createdDate = Instant.now();
    private final Instant filterStartDate = Instant.parse("2000-01-01T00:00:00Z");
    private final Instant filterEndDate = Instant.parse("4000-01-01T00:00:00Z");
    private final PageRequest pageRequest = PageRequest.of(0, 20);

    @Test
    @DisplayName("[단위][Use Case] Comment 상세 조회 - 성공 테스트")
    void readCommentUseCaseSuccessTest() {
        //given
        FindCommentDetailCommand findCommentDetailCommand = new FindCommentDetailCommand(commentId);
        CommentDetailResponseDto commentDetailResponseDto = new CommentDetailResponseDto(commentId, category, targetId, userId, content, createdDate);

        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        given(readCommentPort.findById(anyLong())).willReturn(Optional.of(comment));
        given(commentUseCaseConverter.commentToDetailResponseDto(any(Comment.class))).willReturn(commentDetailResponseDto);
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

        given(readCommentPort.findById(anyLong())).willReturn(Optional.empty());

        //when then
        Assertions.assertThrows(NotFoundResourceException.class, () -> readCommentUseCase.findCommentsDetail(findCommentDetailCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] Comment 상세 조회 comment already deleted - 실패 테스트")
    void readCommentUseCaseCommentAlreadyDeletedFailTest() {
        //given
        FindCommentDetailCommand findCommentDetailCommand = new FindCommentDetailCommand(commentId);

        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        comment.deleteComment();

        given(readCommentPort.findById(anyLong())).willReturn(Optional.of(comment));

        //when then
        Assertions.assertThrows(BadRequestException.class, () -> readCommentUseCase.findCommentsDetail(findCommentDetailCommand));
    }

    @Test
    @DisplayName("[단위][Use Case] Comment Filter - 성공 테스트")
    void commentFilterUseCaseSuccessTest() {
        //given
        CommentFilterCommand findCommentDetailCommand = new CommentFilterCommand(category, targetId, userId, content, filterStartDate, filterEndDate, pageRequest);

        FilteredCommentResponseDto filteredCommentResponseDto = new FilteredCommentResponseDto(commentId, category, targetId, userId, content, createdDate);

        RegisterCommentCommand registerCommentCommand = new RegisterCommentCommand(userId, category, targetId, content);
        Comment comment = Comment.createNewCommentByCommand(registerCommentCommand);

        given(readCommentPort.filterComment(any(CommentFilterCommand.class))).willReturn(List.of(comment));
        given(commentUseCaseConverter.commentToFilteredResponse(any(Comment.class))).willReturn(filteredCommentResponseDto);
        //when
        Slice<FilteredCommentResponseDto> result = readCommentUseCase.filterComment(findCommentDetailCommand);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getContent());
        Assertions.assertFalse(result.getContent().isEmpty());
    }
}