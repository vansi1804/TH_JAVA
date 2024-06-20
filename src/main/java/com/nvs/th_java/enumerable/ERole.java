package com.nvs.th_java.enumerable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ERole {
    ADMIN(1),
    USER(2);
    private final long value;
}
