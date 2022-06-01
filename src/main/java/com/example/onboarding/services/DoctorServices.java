package com.example.onboarding.services;

import com.example.onboarding.dtos.*;
import com.example.onboarding.exceptions.GeneralServiceException;
import com.example.onboarding.exceptions.RegistrationException;
import org.springframework.http.HttpStatus;

public interface DoctorServices {
    DoctorRegistrationResponseDto  registerDoctor(DoctorRegistrationRequestDto doctorRegistrationRequestDto) throws RegistrationException;
    DoctorsListDto fetchAllDoctors();
    DoctorResponseDto fetchSingleDoctor(String email);
    UpdateDoctorAddressResponseDto editADoctor(UpdateDoctorAddressRequestDto updateDoctorAddressRequestDto) throws GeneralServiceException;
    HttpStatus removeADoctorRecord(String email) throws GeneralServiceException;

}
