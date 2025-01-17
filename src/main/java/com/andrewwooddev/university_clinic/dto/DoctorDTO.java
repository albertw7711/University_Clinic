package com.andrewwooddev.university_clinic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorDTO {

  private Long id;
  private String firstName;
  private String lastName;
  private String speciality;
  private String email;
  private String phone;
  private BigDecimal rate;
  private String photoURL;
  private String description;
  private String role;
  private List<AppointmentDTO> appointments = new ArrayList<>();
}
