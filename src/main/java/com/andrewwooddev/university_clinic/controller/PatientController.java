package com.andrewwooddev.university_clinic.controller;


import com.andrewwooddev.university_clinic.dto.Response;
import com.andrewwooddev.university_clinic.service.interfac.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {


  @Autowired
  private IPatientService patientService;


  @GetMapping("/all")
  @PreAuthorize("hasAuthority('a')")
  public ResponseEntity<Response> getAllPatients() {
    Response response = patientService.getAllPatients();
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/get-by-id/{patientId}")
  @PreAuthorize("hasAuthority('a') or hasAuthority('d')")
  public ResponseEntity<Response> getPatientById(@PathVariable("patientId") String patientId) {
    Response response = patientService.getPatientById(patientId);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @DeleteMapping("/delete/{patientId}")
  @PreAuthorize("hasAuthority('a')")
  public ResponseEntity<Response> deletePatient(@PathVariable("patientId") String patientId) {
    Response response = patientService.deletePatient(patientId);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/get-logged-in-profile-info")
  public ResponseEntity<Response> getLoggedInPatientProfile() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    Response response = patientService.getThisPatientInfo(email);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/get-patient-appointments/{patientId}")
  public ResponseEntity<Response> getPatientAppointmentHistory(@PathVariable("patientId") String patientId) {
    Response response = patientService.getPatientAppointmentHistory(patientId);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }


}
