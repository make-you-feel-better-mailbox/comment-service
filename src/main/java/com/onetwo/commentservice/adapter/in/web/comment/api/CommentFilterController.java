package com.onetwo.commentservice.adapter.in.web.comment.api;

import com.onetwo.commentservice.adapter.in.web.comment.mapper.CommentFilterDtoMapper;
import com.onetwo.commentservice.adapter.in.web.comment.request.FilterSliceRequest;
import com.onetwo.commentservice.adapter.in.web.comment.response.FilteredCommentResponse;
import com.onetwo.commentservice.application.port.in.command.CommentFilterCommand;
import com.onetwo.commentservice.application.port.in.response.FilteredCommentResponseDto;
import com.onetwo.commentservice.application.port.in.usecase.ReadCommentUseCase;
import com.onetwo.commentservice.common.GlobalUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentFilterController {

    private final ReadCommentUseCase readCommentUseCase;
    private final CommentFilterDtoMapper commentFilterDtoMapper;


    /**
     * Get Filtered comment inbound adapter
     *
     * @param filterSliceRequest filter condition and pageable
     * @return content and slice data
     */
    @GetMapping(GlobalUrl.COMMENT_FILTER)
    public ResponseEntity<Slice<FilteredCommentResponse>> filterComment(@ModelAttribute FilterSliceRequest filterSliceRequest) {
        CommentFilterCommand commentFilterCommand = commentFilterDtoMapper.filterRequestToCommand(filterSliceRequest);
        Slice<FilteredCommentResponseDto> filteredCommentResponseDto = readCommentUseCase.filterComment(commentFilterCommand);
        return ResponseEntity.ok().body(commentFilterDtoMapper.dtoToFilteredCommentResponse(filteredCommentResponseDto));
    }
}
