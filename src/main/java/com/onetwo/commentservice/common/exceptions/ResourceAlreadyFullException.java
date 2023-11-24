package com.onetwo.commentservice.common.exceptions;

import lombok.Getter;

@Getter
public class ResourceAlreadyFullException extends RuntimeException {

    public ResourceAlreadyFullException(String message) {
        super(message);
    }
}
