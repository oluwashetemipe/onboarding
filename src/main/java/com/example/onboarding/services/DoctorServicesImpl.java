package com.example.onboarding.services;

import com.example.onboarding.dtos.*;
import com.example.onboarding.exceptions.GeneralServiceException;
import com.example.onboarding.exceptions.RegistrationException;
import com.example.onboarding.model.Address;
import com.example.onboarding.model.Doctor;
import com.example.onboarding.repositories.AddressRepository;
import com.example.onboarding.repositories.DoctorRepository;
import com.example.onboarding.util.IdGenerator;
import com.example.onboarding.util.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class DoctorServicesImpl implements DoctorServices{
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public DoctorRegistrationResponseDto  registerDoctor(DoctorRegistrationRequestDto doctorRegistrationRequestDto) throws RegistrationException {
        //check all fields
        checkAllParameters(doctorRegistrationRequestDto);
        //check if email exists
        doctorWithEmailExists(doctorRegistrationRequestDto);
        //register a doctor
        Doctor doctor = createDoctorFromDetails(doctorRegistrationRequestDto);


        return generateRegistrationResponse(doctorRegistrationRequestDto, doctor);
    }

    @Override
    public DoctorsListDto fetchAllDoctors() {
        List<Doctor>doctorList=doctorRepository.findAll();
        DoctorsListDto doctorsListDto=new DoctorsListDto();
        List<DoctorResponseDto>doctorResponseDtoList=new ArrayList<>();
        for(Doctor doctor:doctorList){
            DoctorResponseDto doctorResponseDto=new DoctorResponseDto();
            modelMapper.map(doctor, doctorResponseDto);
            Optional<Address>address=addressRepository.findById(doctor.getAddress());
            if(address.isPresent()){

                modelMapper.map(address.get(), doctorResponseDto);
            }
            doctorResponseDtoList.add(doctorResponseDto);

        }
        doctorsListDto.setDoctorResponseDtoList(doctorResponseDtoList);
        return doctorsListDto;
    }

    @Override
    public DoctorResponseDto fetchSingleDoctor(String email) {
        Optional<Doctor>doctor = doctorRepository.findDoctorByEmail(email);
        DoctorResponseDto doctorResponseDto = new DoctorResponseDto();
        if (doctor.isPresent()){
            modelMapper.map(doctor.get(), doctorResponseDto);
            Optional<Address>address=addressRepository.findById(doctor.get().getAddress());
            if(address.isPresent()){

                modelMapper.map(address.get(),doctorResponseDto );

            }
        }
        return doctorResponseDto;
    }

    @Override
    public UpdateDoctorAddressResponseDto editADoctor(UpdateDoctorAddressRequestDto updateDoctorAddressRequestDto) throws GeneralServiceException {
       Optional<Doctor> doctorOptional= doctorRepository.findDoctorByEmail(updateDoctorAddressRequestDto.getEmail());
       if(doctorOptional.isEmpty()){
           throw new GeneralServiceException("User not found");
       }
       Doctor doctor=doctorOptional.get();
      String id= doctor.getAddress();
      Optional<Address> addressOptional=addressRepository.findById(id);
      if(addressOptional.isEmpty()){
          throw new GeneralServiceException("Address not found");
      }
      Address address=addressOptional.get();
      modelMapper.map(updateDoctorAddressRequestDto,address);
      addressRepository.save(address);
      doctor.setAddress(address.getId());
      doctorRepository.save(doctor);
      UpdateDoctorAddressResponseDto updateDoctorAddressResponseDto=new UpdateDoctorAddressResponseDto();
      modelMapper.map(updateDoctorAddressRequestDto,updateDoctorAddressResponseDto);
      updateDoctorAddressResponseDto.setAddressId(address.getId());
        return updateDoctorAddressResponseDto;
    }


    @Override
    public HttpStatus removeADoctorRecord(String email) throws GeneralServiceException {
    Optional<Doctor> doctor = doctorRepository.findDoctorByEmail(email);
        if (doctor.isEmpty()){
            throw new GeneralServiceException("User not found");
        }
        //something fishy going on
        doctorRepository.delete(doctor.get());
        return HttpStatus.OK;
    }

    private void doctorWithEmailExists(DoctorRegistrationRequestDto doctorRegistrationRequestDto) throws  RegistrationException {

        Optional<Doctor> findDoctorBy = doctorRepository.findDoctorByEmail(doctorRegistrationRequestDto.getEmail());
        if(findDoctorBy.isPresent()){
            throw new RegistrationException("Doctor with this email already exists");
        }

    }
    private Doctor createDoctorFromDetails(DoctorRegistrationRequestDto doctorRegistrationRequestDto) {
        Doctor doctor = new Doctor();
        doctor.setId(IdGenerator.generateId());
        modelMapper.map(doctorRegistrationRequestDto, doctor);
        doctor.setAddress(createAddress(doctorRegistrationRequestDto).getId());
        doctorRepository.save(doctor);
        mapDoctorToAddress(doctor);
        return doctor;
    }
    private void mapDoctorToAddress(Doctor doctor){
        Address address=addressRepository.findById(doctor.getAddress()).get();
        address.setDoctor(doctor.getId());
        addressRepository.save(address);

    }
    private Address createAddress(DoctorRegistrationRequestDto doctorRegistrationRequestDto){
        Address address = new Address();
        modelMapper.map(doctorRegistrationRequestDto, address);
        address.setId(IdGenerator.generateId());
        addressRepository.save(address);
        return address;
    }
    private DoctorRegistrationResponseDto  generateRegistrationResponse(DoctorRegistrationRequestDto doctorRegistrationRequestDto, Doctor doctor){
        DoctorRegistrationResponseDto  doctorResponseDto = new DoctorRegistrationResponseDto();
        modelMapper.map(doctorRegistrationRequestDto, doctorResponseDto);
        doctorResponseDto.setDoctorId(doctor.getId());
        doctorResponseDto.setAddressId(doctor.getAddress());
        return doctorResponseDto;
    }

    private void checkAllParameters(DoctorRegistrationRequestDto doctorRegistrationRequestDto ) throws RegistrationException {

        if (StringUtil.isBlank(doctorRegistrationRequestDto.getFirstName())) {
            throw new RegistrationException("FirstName Cannot be empty");
        }
        if (StringUtil.isBlank(doctorRegistrationRequestDto.getLastName())) {
            throw new RegistrationException("LastName Cannot be empty");
        }
        if (StringUtil.isBlank(doctorRegistrationRequestDto.getPhoneNumber())){
            throw new RegistrationException("Phone number cannot be empty");
        }
        if (StringUtil.isBlank(doctorRegistrationRequestDto.getEmail())){
            throw new RegistrationException("Email cannot be empty");
        }
        if (StringUtil.isBlank(doctorRegistrationRequestDto.getHomeAddress())){
            throw new RegistrationException("Home address cannot be empty");
        }
        if (StringUtil.isBlank(doctorRegistrationRequestDto.getState())){
            throw new RegistrationException("State cannot be empty");
        }
        if (StringUtil.isBlank(doctorRegistrationRequestDto.getLga())){
            throw new RegistrationException("Lga cannot be empty");
        }
    }
}
