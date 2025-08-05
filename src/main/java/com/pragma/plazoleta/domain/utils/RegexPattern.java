package com.pragma.plazoleta.domain.utils;

public class RegexPattern {
    private RegexPattern() {}
    
    public static final String ORDER_STATUS_PATTERN = "^(PENDING|IN_PREPARATION|READY|DELIVERED|CANCELLED)$";
    public static final String EMAIL_PATTERN_REQUIRED = "^(?!.*\\.\\.)[a-zA-Z0-9]([a-zA-Z0-9._%+-]*[a-zA-Z0-9])?@[a-zA-Z0-9]([a-zA-Z0-9.-]*[a-zA-Z0-9])?\\.[a-zA-Z]{2,}$";
    public static final String UUID_PATTERN = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
}
