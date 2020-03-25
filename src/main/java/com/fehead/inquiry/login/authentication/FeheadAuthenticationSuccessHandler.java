package com.fehead.inquiry.login.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fehead.inquiry.controller.UserLoginState;
import com.fehead.inquiry.login.UserAuthenticationToken;
import com.fehead.inquiry.properties.InquiryProperties;
import com.fehead.lang.response.AuthenticationReturnType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @author lmwis on 2019-07-16 10:52
 */
@Component
public class FeheadAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(FeheadAuthenticationSuccessHandler.class);

    final InquiryProperties inquiryProperties;

    final ObjectMapper objectMapper;

    public FeheadAuthenticationSuccessHandler(InquiryProperties inquiryProperties,ObjectMapper objectMapper){
        this.inquiryProperties = inquiryProperties;
        this.objectMapper = objectMapper;
    }

    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        UserAuthenticationToken authentication)
            throws IOException {

        logger.info("登陆成功:" + authentication.getPrincipal());
        String key = inquiryProperties.getJwtKey();
        String token = Jwts.builder()
                .setSubject(objectMapper.writeValueAsString(authentication.getPrincipal()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        setCORS(response);

        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("token", "Bearer " + token);
        response.setHeader("Access-Control-Expose-Headers", "token");
        response.getWriter().write(objectMapper
                .writeValueAsString(
                        AuthenticationReturnType.create(
                                ((UserLoginState)authentication.getPrincipal())
                                        .getDoctorDO(), HttpStatus.OK.value())));

    }

    public static void setCORS(HttpServletResponse response){
        //跨域设置
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    }
}
