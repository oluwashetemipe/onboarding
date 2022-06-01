package com.example.onboarding.services;

import com.example.onboarding.dtos.*;
import com.example.onboarding.exceptions.GeneralServiceException;
import com.example.onboarding.exceptions.RegistrationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
class DoctorServicesImplTest {
    @Autowired
    DoctorServices doctorServices;

    DoctorRegistrationRequestDto registrationRequestDto;

    @BeforeEach
    void setUp() {
        registrationRequestDto = new DoctorRegistrationRequestDto();
    }

    @AfterEach
    void tearDown() {
        registrationRequestDto = null;
    }
    @Test
    void setRegistrationRequestDto() throws RegistrationException {
        registrationRequestDto.setFirstName("Malik");
        registrationRequestDto.setLastName("Omolola");
        registrationRequestDto.setEmail("Omololamalik@gmail.com");
        registrationRequestDto.setPhoneNumber("052365687");
        registrationRequestDto.setHomeAddress("19, kad str");
        registrationRequestDto.setLga("yaba");
        registrationRequestDto.setState("Lagos");
        DoctorRegistrationResponseDto doctor = doctorServices.registerDoctor(registrationRequestDto);

    }

    @Test
    void  fetchSingleDoctor(){
        DoctorResponseDto doctor = doctorServices.fetchSingleDoctor("Omololamalik@gmail.com");
    }

    @Test
    void  fetchAllDoctors(){
        DoctorsListDto doctor = doctorServices.fetchAllDoctors();
    }

    @Test
    void  deleteDoctor() throws GeneralServiceException {
        HttpStatus doctor = doctorServices.removeADoctorRecord("Omololamalik@gmail.com");
    }

    @Test
    UpdateDoctorAddressRequestDto updateAddress() throws GeneralServiceException {
        UpdateDoctorAddressResponseDto doctor = doctorServices.editADoctor(updateAddress());
        return null;
    }


}