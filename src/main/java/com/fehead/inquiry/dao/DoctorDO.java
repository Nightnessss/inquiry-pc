package com.fehead.inquiry.dao;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Nightnessss 2020/3/25 8:50
 */
@Data
@TableName("doctor_info")
public class DoctorDO extends User {

    private String hospital;

    private String office;
}
