package com.onetwo.commentservice.adapter.in.web.comment.mapper;

import com.onetwo.commentservice.adapter.in.web.comment.request.FilterSliceRequest;
import com.onetwo.commentservice.adapter.in.web.comment.response.FilteredCommentResponse;
import com.onetwo.commentservice.application.port.in.command.CommentFilterCommand;
import com.onetwo.commentservice.application.port.in.response.FilteredCommentResponseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentFilterDtoMapperImpl implements CommentFilterDtoMapper {
    @Override
    public CommentFilterCommand filterRequestToCommand(FilterSliceRequest filterSliceRequest) {
        Pageable pageable = PageRequest.of(
                filterSliceRequest.pageNumber() == null ? 0 : filterSliceRequest.pageNumber(),
                filterSliceRequest.pageSize() == null ? 10 : filterSliceRequest.pageSize()
        );

        return new CommentFilterCommand(
                filterSliceRequest.category(),
                filterSliceRequest.targetId(),
                filterSliceRequest.userId(),
                filterSliceRequest.content(),
                filterSliceRequest.filterStartDate(),
                filterSliceRequest.filterEndDate(),
                pageable
        );
    }

    @Override
    public Slice<FilteredCommentResponse> dtoToFilteredCommentResponse(Slice<FilteredCommentResponseDto> filteredCommentResponseDto) {
        List<FilteredCommentResponse> filteredCommentResponseList = filteredCommentResponseDto.getContent().stream()
                .map(response -> new FilteredCommentResponse(
                        response.commentId(),
                        response.category(),
                        response.targetId(),
                        response.userId(),
                        response.content(),
                        response.createdDate()
                )).toList();

        return new SliceImpl<>(filteredCommentResponseList, filteredCommentResponseDto.getPageable(), filteredCommentResponseDto.hasNext());
    }
}
