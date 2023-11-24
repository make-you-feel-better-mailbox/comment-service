package com.onetwo.commentservice.adapter.in.web.comment.api;

import com.onetwo.commentservice.adapter.in.web.comment.mapper.CommentDtoMapper;
import com.onetwo.commentservice.adapter.in.web.comment.request.RegisterCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.response.DeleteCommentResponse;
import com.onetwo.commentservice.adapter.in.web.comment.response.RegisterCommentResponse;
import com.onetwo.commentservice.application.port.in.command.DeleteCommentCommand;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.response.DeleteCommentResponseDto;
import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;
import com.onetwo.commentservice.application.port.in.usecase.DeleteCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.RegisterCommentUseCase;
import com.onetwo.commentservice.common.GlobalUrl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final RegisterCommentUseCase registerCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final CommentDtoMapper commentDtoMapper;

    /**
     * Register Comment inbound adapter
     *
     * @param registerCommentRequest data about register comment
     * @param userId                 user authentication id
     * @return Boolean about register success
     */
    @PostMapping(GlobalUrl.COMMENT_ROOT)
    public ResponseEntity<RegisterCommentResponse> registerComment(@RequestBody @Valid RegisterCommentRequest registerCommentRequest,
                                                                   @AuthenticationPrincipal String userId) {
        RegisterCommentCommand registerCommentCommand = commentDtoMapper.registerRequestToCommand(userId, registerCommentRequest);
        RegisterCommentResponseDto registerCommentResponseDto = registerCommentUseCase.registerComment(registerCommentCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDtoMapper.dtoToRegisterResponse(registerCommentResponseDto));
    }

    /**
     * Delete Comment inbound adapter
     *
     * @param commentId Request delete comment id
     * @param userId    user authentication id
     * @return Boolean about delete comment success
     */
    @DeleteMapping(GlobalUrl.COMMENT_ROOT + GlobalUrl.PATH_VARIABLE_COMMENT_ID_WITH_BRACE)
    public ResponseEntity<DeleteCommentResponse> deleteComment(@PathVariable(GlobalUrl.PATH_VARIABLE_COMMENT_ID) Long commentId,
                                                               @AuthenticationPrincipal String userId) {
        DeleteCommentCommand deleteCommentCommand = commentDtoMapper.deleteRequestToCommand(userId, commentId);
        DeleteCommentResponseDto deleteCommentResponseDto = deleteCommentUseCase.deleteComment(deleteCommentCommand);
        return ResponseEntity.ok().body(commentDtoMapper.dtoToDeleteResponse(deleteCommentResponseDto));
    }
}
