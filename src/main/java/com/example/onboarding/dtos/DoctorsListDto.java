package com.example.onboarding.dtos;

import java.util.ArrayList;
import java.util.List;

public class DoctorsListDto {
    List<DoctorResponseDto>doctorResponseDtoList=new ArrayList<>();

    public List<DoctorResponseDto> getDoctorResponseDtoList() {
        return doctorResponseDtoList;
    }

    public void setDoctorResponseDtoList(List<DoctorResponseDto> doctorResponseDtoList) {
        this.doctorResponseDtoList = doctorResponseDtoList;
    }
}
