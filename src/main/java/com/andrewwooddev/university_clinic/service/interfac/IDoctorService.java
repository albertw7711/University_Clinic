package com.andrewwooddev.university_clinic.service.interfac;

import com.andrewwooddev.university_clinic.dto.LoginRequest;
import com.andrewwooddev.university_clinic.dto.Response;
import com.andrewwooddev.university_clinic.entity.Doctor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IDoctorService {

  List<String> getAllDoctorTypes();


  Response updateDoctor(Long doctorId, String firstName, String lastName,
                        String email, String phone, BigDecimal rate,
                        String description, MultipartFile photo);

  Response getAvailableDoctorsByTimeAndSpeciality(LocalDateTime startTime,
                                                  LocalDateTime endTime,
                                       String speciality);

  Response getAllAvailableDoctors();

  Response register(Doctor doctor);

  Response login(LoginRequest loginRequest);

  Response getAllDoctors();

  Response getDoctorAppointmentHistory(String doctorId);

  Response deleteDoctor(String doctorId);

  Response getDoctorById(String doctorId);

  Response getThisDoctorInfo(String email);

}
