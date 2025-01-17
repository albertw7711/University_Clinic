package com.andrewwooddev.university_clinic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "appointments")
public class Appointment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "appointment time is required")
  private LocalDateTime startTime;

  private LocalDateTime endTime;

  private String confirmationCode;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "doctor_id")
  private Doctor doctor;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "patient_id")
  private Patient patient;

  private BigDecimal cost;

  @Override
  public String toString() {
    return "Booking{" +
        "id=" + id +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", cost=" + cost +
        ", confirmationCode='" + confirmationCode + '\'' +
        '}';
  }
}
