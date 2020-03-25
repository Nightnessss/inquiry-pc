package com.fehead.inquiry.controller;

import com.fehead.inquiry.dao.DoctorDO;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description: 加密成token
 * @Author: lmwis
 * @Date 2020-03-22 13:26
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class UserLoginState {

    private DoctorDO DoctorDO;

}
