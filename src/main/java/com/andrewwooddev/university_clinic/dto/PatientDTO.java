package com.andrewwooddev.university_clinic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatientDTO {
  
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String role;
  private List<AppointmentDTO> appointments = new ArrayList<>();
}
