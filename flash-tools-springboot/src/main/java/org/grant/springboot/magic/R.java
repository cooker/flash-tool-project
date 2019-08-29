package org.grant.springboot.magic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class R {
    public final static int FAIL = 0;
    public final static int OK = 1;

    private int code;
    private String message;
    private Object body;
}