package com.fehead.inquiry.login;

import lombok.Data;

/**
 * @Description:
 * @Author: lmwis
 * @Date 2020-03-21 15:49
 * @Version 1.0
 */
@Data
public class UserAuthenticationToken {

    private Object principal;

    public UserAuthenticationToken(Object principal) {
        this.principal = principal;
    }
}
