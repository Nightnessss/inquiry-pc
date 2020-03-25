package com.fehead.inquiry.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author Nightnessss 2020/3/25 15:23
 */
@TableName("message")
@Data
public class MessageDO {
    public static final int TEXT = 0;
    public static final int PIC = 1;
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String userId;

    private String receiverId;

    private Date time;

    private String content;

    private Integer type;
}
