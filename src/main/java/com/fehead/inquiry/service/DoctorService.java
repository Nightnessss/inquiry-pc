package com.fehead.inquiry.service;

import com.fehead.inquiry.controller.vo.MessageVO;
import com.fehead.inquiry.controller.vo.PatientListVO;
import com.fehead.inquiry.controller.vo.PatientVO;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

/**
 * @author Nightnessss 2020/3/25 15:03
 */
public interface DoctorService {

    public List<PatientListVO> getAllPatients() throws IOException;

    public PatientVO getPatientById(String patientId);

    public List<MessageVO> getMessageByPatientId(String patientId, Pageable pageable);
}
