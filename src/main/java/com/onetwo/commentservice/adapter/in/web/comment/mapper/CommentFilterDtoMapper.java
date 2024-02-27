
package com.onetwo.commentservice.adapter.in.web.comment.mapper;

import com.onetwo.commentservice.adapter.in.web.comment.request.FilterSliceRequest;
import com.onetwo.commentservice.adapter.in.web.comment.response.FilteredCommentResponse;
import com.onetwo.commentservice.application.port.in.command.CommentFilterCommand;
import com.onetwo.commentservice.application.port.in.response.FilteredCommentResponseDto;
import org.springframework.data.domain.Slice;

public interface CommentFilterDtoMapper {
    CommentFilterCommand filterRequestToCommand(FilterSliceRequest filterSliceRequest);

    Slice<FilteredCommentResponse> dtoToFilteredCommentResponse(Slice<FilteredCommentResponseDto> filteredCommentResponseDto);
}
