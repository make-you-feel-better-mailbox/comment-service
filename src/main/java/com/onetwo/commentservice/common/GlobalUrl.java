package com.onetwo.commentservice.common;

public class GlobalUrl {

    public static final String ROOT_URI = "/";
    public static final String UNDER_ROUTE = "/*";
    public static final String EVERY_UNDER_ROUTE = "/**";
    public static final String COMMENT_ROOT = "/comments";
    public static final String COMMENT_FILTER = COMMENT_ROOT + "/filter";

    public static final String PATH_VARIABLE_COMMENT_ID = "comment-id";
    public static final String PATH_VARIABLE_COMMENT_ID_WITH_BRACE = "/{" + PATH_VARIABLE_COMMENT_ID + "}";
}
