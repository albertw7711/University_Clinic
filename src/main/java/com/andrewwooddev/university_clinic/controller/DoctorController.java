package com.andrewwooddev.university_clinic.controller;


import com.andrewwooddev.university_clinic.dto.Response;
import com.andrewwooddev.university_clinic.service.interfac.IAppointmentService;
import com.andrewwooddev.university_clinic.service.interfac.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

  @Autowired
  private IDoctorService doctorService;
  @Autowired
  private IAppointmentService iAppointmentService;

  @GetMapping("/all")
  public ResponseEntity<Response> getAllDoctors() {
    Response response = doctorService.getAllDoctors();
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/specialties")
  public List<String> getDoctorTypes() {
    return doctorService.getAllDoctorTypes();
  }

  @GetMapping("/doctor-by-id/{doctorId}")
  public ResponseEntity<Response> getDoctorById(@PathVariable String doctorId) {
    Response response = doctorService.getDoctorById(doctorId);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/all-available-doctors")
  public ResponseEntity<Response> getAvailableDoctors() {
    Response response = doctorService.getAllAvailableDoctors();
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/available-doctors-by-time-and-specialty")
  public ResponseEntity<Response> getAvailableDoctorsByTimeAndSpecialty(
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
      @RequestParam(required = false) String doctorType
  ) {
    if (startTime == null || doctorType == null || doctorType.isBlank() || endTime == null) {
      Response response = new Response();
      response.setStatusCode(400);
      response.setMessage("Please provide values for all fields(startTime, doctorType," +
          "endTime)");
      return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    Response response = doctorService.getAvailableDoctorsByTimeAndSpeciality(startTime, endTime,
        doctorType);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @PutMapping("/update/{doctorId}")
  @PreAuthorize("hasAuthority('a')")
  public ResponseEntity<Response> updateDoctor() {
    Response response = new Response();
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @DeleteMapping("/delete/{doctorId}")
  @PreAuthorize("hasAuthority('a')")
  public ResponseEntity<Response> deleteDoctor(@PathVariable String doctorId) {
    Response response = doctorService.deleteDoctor(doctorId);
    return ResponseEntity.status(response.getStatusCode()).body(response);

  }

  @GetMapping("/get-logged-in-profile-info")
  public ResponseEntity<Response> getLoggedInDoctorProfile() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    Response response = doctorService.getThisDoctorInfo(email);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/get-doctor-appointments/{doctorId}")
  public ResponseEntity<Response> getDoctorAppointmentHistory(@PathVariable("doctorId") String doctorId) {
    Response response = doctorService.getDoctorAppointmentHistory(doctorId);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

}
