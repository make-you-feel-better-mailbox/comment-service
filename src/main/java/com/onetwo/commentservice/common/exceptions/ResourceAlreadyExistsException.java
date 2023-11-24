package com.onetwo.commentservice.common.exceptions;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
