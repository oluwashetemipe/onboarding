package com.example.onboarding.controller;

import com.example.onboarding.dtos.DoctorRegistrationRequestDto;
import com.example.onboarding.dtos.UpdateDoctorAddressRequestDto;
import com.example.onboarding.model.Doctor;
import com.example.onboarding.services.DoctorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")

public class DoctorController {
    @Autowired
    DoctorServices doctorServices;


    @PostMapping("/register-doctor")
    public ResponseEntity<?> Registration(@RequestBody DoctorRegistrationRequestDto doctorRegistrationRequestDto) {
        try {
            //entry point
            return new ResponseEntity<>(doctorServices.registerDoctor(doctorRegistrationRequestDto), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/fetch-all-doctors")
    public ResponseEntity<?> fetchAllDoctors() {
        try {
            return new ResponseEntity<>(doctorServices.fetchAllDoctors(), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/fetch-a-single-doctor/{email}")
    public ResponseEntity<?> fetchASingleDoctor(@PathVariable("email") String email) {
        try {
            return new ResponseEntity<>(doctorServices.fetchSingleDoctor(email), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }
    @DeleteMapping("/delete-doctor-record/{email}")
    public ResponseEntity<?> deleteDoctorRecord(@PathVariable("email")  String email){
        try{
            return new ResponseEntity<>(doctorServices.removeADoctorRecord(email), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/edit-doctor-address")
    public ResponseEntity<?> editDoctorRecord(@RequestBody UpdateDoctorAddressRequestDto updateDoctorAddressRequestDto){
        try{
            return new ResponseEntity<>(doctorServices.editADoctor(updateDoctorAddressRequestDto), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
