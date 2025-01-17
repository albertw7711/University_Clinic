package com.andrewwooddev.university_clinic.controller;

import com.andrewwooddev.university_clinic.dto.Response;
import com.andrewwooddev.university_clinic.entity.Appointment;
import com.andrewwooddev.university_clinic.service.interfac.IAppointmentService;
import com.andrewwooddev.university_clinic.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")

public class AppointmentController {

  @Autowired
  private IAppointmentService appointmentService;

  @PostMapping("/make-appointment/{doctorId}/{patientId}")
  @PreAuthorize("hasAuthority('a') or hasAuthority('p')")
  public ResponseEntity<Response> savePatientAppointments(@PathVariable Long doctorId,
                                               @PathVariable Long patientId,
                                               @RequestBody Appointment appointmentRequest) {


    Response response = appointmentService.saveAppointment(doctorId, patientId,
        appointmentRequest);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }


  @GetMapping("/all")
  @PreAuthorize("hasAuthority('a')")
  public ResponseEntity<Response> getAllAppointments() {
    Response response = appointmentService.getAllAppointments();
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }

  @GetMapping("/get-by-confirmation-code/{confirmationCode}")
  public ResponseEntity<Response> getAppointmentByConfirmationCode(@PathVariable String confirmationCode, @PathVariable String role) {
    Response response = appointmentService.findAppointmentByConfirmationCode(confirmationCode,
        role);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }


  @DeleteMapping("/cancel/{appointmentId}")
  public ResponseEntity<Response> cancelAppointment(@PathVariable Long appointmentId) {
    Response response = appointmentService.cancelAppointment(appointmentId);
    return ResponseEntity.status(response.getStatusCode()).body(response);
  }


}
