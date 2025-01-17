package com.andrewwooddev.university_clinic.controller;

import com.andrewwooddev.university_clinic.dto.LoginRequest;
import com.andrewwooddev.university_clinic.dto.Response;
import com.andrewwooddev.university_clinic.entity.Patient;
import com.andrewwooddev.university_clinic.entity.Doctor;
import com.andrewwooddev.university_clinic.service.interfac.IPatientService;
import com.andrewwooddev.university_clinic.service.interfac.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private IPatientService patientService;

  @Autowired
  private IDoctorService doctorService;

  @PostMapping("/register/patient")
  public ResponseEntity<Response> registerPatient(@RequestBody Patient patient) {
    Response response = patientService.register(patient);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @PostMapping("/register/doctor")
  public ResponseEntity<Response> registerDoctor(@RequestBody Doctor doctor) {
    Response response = doctorService.register(doctor);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @PostMapping("/login/patient")
  public ResponseEntity<Response> loginPatient(@RequestBody LoginRequest loginRequest) {
    Response response = patientService.login(loginRequest);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @PostMapping("/login/doctor")
  public ResponseEntity<Response> loginDoctor(@RequestBody LoginRequest loginRequest) {
    Response response = doctorService.login(loginRequest);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }
}
