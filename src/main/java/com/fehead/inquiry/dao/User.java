package com.fehead.inquiry.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Description:
 * @Author: lmwis
 * @Date 2020-03-21 15:54
 * @Version 1.0
 */
@Data
public class User {

    @TableId
    private String id;

    private String name;

    private Short gender;

    private Short age;

    private String phone;

    @TableField("third_id")
    private String thirdId;

}
