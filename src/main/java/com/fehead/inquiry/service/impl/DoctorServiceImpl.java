package com.fehead.inquiry.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fehead.inquiry.controller.UserLoginState;
import com.fehead.inquiry.controller.vo.MessageVO;
import com.fehead.inquiry.controller.vo.PatientListVO;
import com.fehead.inquiry.controller.vo.PatientVO;
import com.fehead.inquiry.dao.MessageDO;
import com.fehead.inquiry.dao.PatientDO;
import com.fehead.inquiry.dao.mapper.MessageMapper;
import com.fehead.inquiry.dao.mapper.PatientMapper;
import com.fehead.inquiry.login.SecurityContext;
import com.fehead.inquiry.service.DoctorService;
import org.springframework.beans.BeanUtils;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nightnessss 2020/3/25 15:03
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    private final PatientMapper patientMapper;
    private final MessageMapper messageMapper;
    private final ObjectMapper objectMapper;
    private final SecurityContext securityContext;

    public DoctorServiceImpl(PatientMapper patientMapper, MessageMapper messageMapper, ObjectMapper objectMapper, SecurityContext securityContext) {
        this.patientMapper = patientMapper;
        this.messageMapper = messageMapper;
        this.objectMapper = objectMapper;
        this.securityContext = securityContext;
    }

    @Override
    public List<PatientListVO> getAllPatients() throws IOException {

        String tokenStr = (String)securityContext.getToken().getPrincipal();
        UserLoginState user = JSONObject.parseObject(tokenStr, UserLoginState.class);
        List<PatientDO> patientDOList = patientMapper.selectDoctorConnectPatientById(user.getDoctorDO().getId());
        List<PatientListVO> patientListVOList = new ArrayList<>();
        for (PatientDO patientDO : patientDOList) {
            PatientListVO patientListVO = new PatientListVO();
            BeanUtils.copyProperties(patientDO, patientListVO);
            patientListVOList.add(patientListVO);
        }
        return patientListVOList;
    }

    @Override
    public PatientVO getPatientById(String patientId) {

        PatientDO patientDO = patientMapper.selectById(patientId);
        PatientVO patientVO = new PatientVO();
        BeanUtils.copyProperties(patientDO, patientVO);
        return patientVO;
    }

    @Override
    public List<MessageVO> getMessageByPatientId(String patientId, Pageable pageable) {
        QueryWrapper<MessageDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver_id", patientId)
                .or().eq("user_id", patientId)
                .orderByAsc("time");
        Page<MessageDO> page = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
        IPage<MessageDO> messageDOIPage = messageMapper.selectPage(page, queryWrapper);
        List<MessageDO> messageDOList = messageDOIPage.getRecords();
        List<MessageVO> messageVOList = new ArrayList<>();
        for (MessageDO messageDO : messageDOList) {
            MessageVO messageVO = new MessageVO();
            BeanUtils.copyProperties(messageDO, messageVO);
            messageVOList.add(messageVO);
        }

        return messageVOList;
    }
}
