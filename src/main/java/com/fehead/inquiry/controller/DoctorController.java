package com.fehead.inquiry.controller;

import com.fehead.inquiry.service.DoctorService;
import com.fehead.lang.controller.BaseController;
import com.fehead.lang.response.CommonReturnType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Nightnessss 2020/3/25 15:00
 */

@RestController
@RequestMapping("api/v1/inquiry-pc")
public class DoctorController extends BaseController {


    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/doctor/patients")
    public CommonReturnType getAllPatients() throws IOException {

        return CommonReturnType.create(doctorService.getAllPatients());
    }

    @GetMapping("/doctor/patient/{id}")
    public CommonReturnType getPatient(@PathVariable("id") String patientId) {

        return CommonReturnType.create(doctorService.getPatientById(patientId));
    }

    @GetMapping("/doctor/patient/{id}/message")
    public CommonReturnType getMessage(@PathVariable("id") String patientId,
                                       @PageableDefault(size = 6,page = 1) Pageable pageable) {

        return CommonReturnType.create(doctorService.getMessageByPatientId(patientId, pageable));
    }
}
