package com.fehead.inquiry.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Nightnessss 2020/3/25 1:08
 */
@TableName("doctor_password")
@Data
public class DoctorPasswordDO {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("doctor_phone")
    private String phone;
    private String password;
}
