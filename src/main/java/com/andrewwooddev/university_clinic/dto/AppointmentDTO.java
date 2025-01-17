package com.andrewwooddev.university_clinic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentDTO {

  private Long id;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String confirmationCode;
  private DoctorDTO doctor;
  private PatientDTO patient;
  private BigDecimal cost;
}
