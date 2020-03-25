package com.fehead.inquiry.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fehead.inquiry.controller.UserLoginState;
import com.fehead.inquiry.dao.DoctorDO;
import com.fehead.inquiry.dao.DoctorPasswordDO;
import com.fehead.inquiry.dao.mapper.DoctorMapper;
import com.fehead.inquiry.dao.mapper.DoctorPasswordMapper;
import com.fehead.inquiry.login.authentication.FeheadAuthenticationFailureHandler;
import com.fehead.inquiry.login.authentication.FeheadAuthenticationSuccessHandler;
import com.fehead.inquiry.properties.InquiryProperties;
import com.fehead.lang.error.AuthenticationException;
import com.fehead.lang.error.EmBusinessError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Nightnessss 2020/3/25 10:39
 */
@Component
public class LoginValidateFilter extends OncePerRequestFilter {

    private static final String LOGIN_URL = "/api/v1/login";

    private final String JwtKey;

    private final FeheadAuthenticationSuccessHandler feheadAuthenticationSuccessHandler;

    private final FeheadAuthenticationFailureHandler feheadAuthenticationFailureHandler;

    private final DoctorPasswordMapper doctorPasswordMapper;

    private final DoctorMapper doctorMapper;

    private final PasswordEncoder passwordEncoder;

    private final SecurityContext securityContext;

    public LoginValidateFilter(InquiryProperties inquiryProperties
            , FeheadAuthenticationSuccessHandler feheadAuthenticationSuccessHandler
            , DoctorPasswordMapper doctorPasswordMapper
            , DoctorMapper doctorMapper
            , FeheadAuthenticationFailureHandler feheadAuthenticationFailureHandler
            , PasswordEncoder passwordEncoder
            , SecurityContext securityContext) {

        this.JwtKey = inquiryProperties.getJwtKey();
        this.feheadAuthenticationSuccessHandler = feheadAuthenticationSuccessHandler;
        this.doctorPasswordMapper = doctorPasswordMapper;
        this.doctorMapper = doctorMapper;
        this.feheadAuthenticationFailureHandler = feheadAuthenticationFailureHandler;
        this.passwordEncoder = passwordEncoder;
        this.securityContext = securityContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (StringUtils.equals(uri, LOGIN_URL)) {
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");
            QueryWrapper<DoctorPasswordDO> wrapper = new QueryWrapper<>();
            wrapper.eq("doctor_phone", phone);
            DoctorPasswordDO passwordEncoded = doctorPasswordMapper.selectOne(wrapper);
            if (passwordEncoder.matches(password, passwordEncoded.getPassword())) {
                QueryWrapper<DoctorDO> doctorQueryWrapper = new QueryWrapper<>();
                doctorQueryWrapper.eq("phone", phone);
                DoctorDO doctorDO = doctorMapper.selectOne(doctorQueryWrapper);
                feheadAuthenticationSuccessHandler.onAuthenticationSuccess(request, response
                        , new UserAuthenticationToken(new UserLoginState(doctorDO)));
                return;
            } else {
                feheadAuthenticationFailureHandler.onAuthenticationFailure(request,response
                        ,new AuthenticationException(EmBusinessError.SERVICE_AUTHENTICATION_INVALID));
                return;
            }
        }
        if (securityContext.getToken() == null) {
            feheadAuthenticationFailureHandler.onAuthenticationFailure(request,response
                ,new AuthenticationException(EmBusinessError.SERVICE_AUTHENTICATION_INVALID));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
