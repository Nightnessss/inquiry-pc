package com.fehead.inquiry.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fehead.inquiry.dao.PatientDO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Nightnessss 2020/3/25 15:13
 */
public interface PatientMapper extends BaseMapper<PatientDO> {

    /**
     * 查询与该医生相关的所有患者
     * @param id
     * @return
     */
    @Select("select * from patient_info where id " +
            "in (select patient_id from doctor_patient_connect where doctor_patient_connect.doctor_id=#{id})")
    List<PatientDO> selectDoctorConnectPatientById(String id);
}
