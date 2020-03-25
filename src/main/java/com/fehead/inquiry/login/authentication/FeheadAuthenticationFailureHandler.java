package com.fehead.inquiry.login.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fehead.lang.error.AuthenticationException;
import com.fehead.lang.response.AuthenticationReturnType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: lmwis
 * @Date 2020-03-22 13:31
 * @Version 1.0
 */
@Component
@RequiredArgsConstructor
public class FeheadAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(FeheadAuthenticationFailureHandler.class);

    final ObjectMapper objectMapper ;

    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        logger.info("登录失败");
        logger.info(exception.getErrorMsg());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper
                .writeValueAsString( AuthenticationReturnType
                        .create(exception.getErrorMsg()
                                ,HttpStatus.FORBIDDEN.value())));
    }
}
