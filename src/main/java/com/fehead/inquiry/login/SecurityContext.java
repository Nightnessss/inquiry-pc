package com.fehead.inquiry.login;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: lmwis
 * @Date 2020-03-21 16:03
 * @Version 1.0
 */
@Component
@Data
public class SecurityContext {

    private UserAuthenticationToken token;

}
