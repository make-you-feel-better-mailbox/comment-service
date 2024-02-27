package com.onetwo.commentservice.adapter.in.web.comment.api;

import com.onetwo.commentservice.adapter.in.web.comment.mapper.CommentDtoMapper;
import com.onetwo.commentservice.adapter.in.web.comment.request.RegisterCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.request.UpdateCommentRequest;
import com.onetwo.commentservice.adapter.in.web.comment.response.*;
import com.onetwo.commentservice.application.port.in.command.DeleteCommentCommand;
import com.onetwo.commentservice.application.port.in.command.FindCommentDetailCommand;
import com.onetwo.commentservice.application.port.in.command.RegisterCommentCommand;
import com.onetwo.commentservice.application.port.in.command.UpdateCommentCommand;
import com.onetwo.commentservice.application.port.in.response.CommentDetailResponseDto;
import com.onetwo.commentservice.application.port.in.response.DeleteCommentResponseDto;
import com.onetwo.commentservice.application.port.in.response.RegisterCommentResponseDto;
import com.onetwo.commentservice.application.port.in.response.UpdateCommentResponseDto;
import com.onetwo.commentservice.application.port.in.usecase.DeleteCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.ReadCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.RegisterCommentUseCase;
import com.onetwo.commentservice.application.port.in.usecase.UpdateCommentUseCase;
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
    private final UpdateCommentUseCase updateCommentUseCase;
    private final ReadCommentUseCase readCommentUseCase;
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
        DeleteCommentCommand deleteCommentCommand = commentDtoMapper.deleteRequestToCommand(commentId, userId);
        DeleteCommentResponseDto deleteCommentResponseDto = deleteCommentUseCase.deleteComment(deleteCommentCommand);
        return ResponseEntity.ok().body(commentDtoMapper.dtoToDeleteResponse(deleteCommentResponseDto));
    }

    /**
     * Update Comment inbound adapter
     *
     * @param commentId            Request Update comment id
     * @param updateCommentRequest Request Update data about comment
     * @param userId               user authentication id
     * @return Boolean about update comment success
     */
    @PutMapping(GlobalUrl.COMMENT_ROOT + GlobalUrl.PATH_VARIABLE_COMMENT_ID_WITH_BRACE)
    public ResponseEntity<UpdateCommentResponse> updateComment(@PathVariable(GlobalUrl.PATH_VARIABLE_COMMENT_ID) Long commentId,
                                                               @RequestBody @Valid UpdateCommentRequest updateCommentRequest,
                                                               @AuthenticationPrincipal String userId) {
        UpdateCommentCommand updateCommentCommand = commentDtoMapper.updateRequestCommand(commentId, userId, updateCommentRequest);
        UpdateCommentResponseDto updateCommentResponseDto = updateCommentUseCase.updateComment(updateCommentCommand);
        return ResponseEntity.ok().body(commentDtoMapper.dtoToUpdateResponse(updateCommentResponseDto));
    }

    /**
     * Get Detail about comment inbound adapter
     *
     * @param commentId Request comment id
     * @return Detail data about comment
     */
    @GetMapping(GlobalUrl.COMMENT_ROOT + GlobalUrl.PATH_VARIABLE_COMMENT_ID_WITH_BRACE)
    public ResponseEntity<CommentDetailResponse> findCommentsDetail(@PathVariable(GlobalUrl.PATH_VARIABLE_COMMENT_ID) Long commentId) {
        FindCommentDetailCommand findCommentDetailCommand = commentDtoMapper.findRequestToCommand(commentId);
        CommentDetailResponseDto commentDetailResponseDto = readCommentUseCase.findCommentsDetail(findCommentDetailCommand);
        return ResponseEntity.ok().body(commentDtoMapper.dtoToDetailResponse(commentDetailResponseDto));
    }

    @GetMapping(GlobalUrl.COMMENT_COUNT + GlobalUrl.PATH_VARIABLE_CATEGORY_WITH_BRACE + GlobalUrl.PATH_VARIABLE_TARGET_ID_WITH_BRACE)
    public ResponseEntity<CommentCountResponse> getCommentCount(@PathVariable(GlobalUrl.PATH_VARIABLE_CATEGORY) Integer category,
                                                                @PathVariable(GlobalUrl.PATH_VARIABLE_TARGET_ID) Long targetId) {
        return null;
    }
}
