package com.fehead.inquiry.controller.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Nightnessss 2020/3/25 15:29
 */
@Data
public class MessageVO {

    private String content;
    private Date time;
    private String type;
}
