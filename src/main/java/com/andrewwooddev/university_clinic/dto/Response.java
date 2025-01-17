package com.andrewwooddev.university_clinic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

  private int statusCode;
  private String message;

  private String token;
  private String role;
  private String expirationTime;

  private String confirmationCode;

  private DoctorDTO doctor;
  private PatientDTO patient;
  private AppointmentDTO appointment;
  private List<DoctorDTO> doctorList;
  private List<PatientDTO> patientList;
  private List<AppointmentDTO> appointmentList;
}
