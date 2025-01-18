package com.andrewwooddev.university_clinic.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "doctors")
public class Doctor implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "First name is required")
  private String firstName;

  @NotBlank(message = "Last name is required")
  private String lastName;

  @NotBlank(message = "Speciality is required")
  private String speciality;

  @NotBlank(message = "Email is required")
  @Column(unique = true)
  private String email;

  private String phone;

  @NotBlank(message = "Password is required")
  private String password;

  @NotBlank(message = "Rate is required")
  private BigDecimal rate;

  private String photo;

  private String description;

  private String role = "d";

  @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Appointment> appointments = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("d"));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String toString() {
    return "Doctor{" +
        "id=" + id +
        ", first=" + firstName +
        ", last=" + lastName +
        ", email=" + email +
        ", specialty=" + speciality +
        ", rate=" + rate + '\'' +
        '}';
  }
}
