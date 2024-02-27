package com.onetwo.commentservice.common;

public class GlobalUrl {

    public static final String ROOT_URI = "/comment-service";
    public static final String UNDER_ROUTE = "/*";
    public static final String EVERY_UNDER_ROUTE = "/**";
    public static final String COMMENT_ROOT = ROOT_URI + "/comments";
    public static final String COMMENT_FILTER = COMMENT_ROOT + "/filter";

    public static final String PATH_VARIABLE_COMMENT_ID = "comment-id";
    public static final String PATH_VARIABLE_COMMENT_ID_WITH_BRACE = "/{" + PATH_VARIABLE_COMMENT_ID + "}";

    public static final String COMMENT_COUNT = COMMENT_ROOT + "/count";

    public static final String PATH_VARIABLE_CATEGORY = "category";
    public static final String PATH_VARIABLE_CATEGORY_WITH_BRACE = "/{" + PATH_VARIABLE_CATEGORY + "}";

    public static final String PATH_VARIABLE_TARGET_ID = "target-id";
    public static final String PATH_VARIABLE_TARGET_ID_WITH_BRACE = "/{" + PATH_VARIABLE_TARGET_ID + "}";
}
