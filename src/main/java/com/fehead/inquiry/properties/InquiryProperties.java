package com.fehead.inquiry.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description:
 * @Author: lmwis
 * @Date 2020-03-21 16:21
 * @Version 1.0
 */
@Data
@ConfigurationProperties(prefix = "inquiry")
public class InquiryProperties {

    private String JwtKey = "the default key";

}
