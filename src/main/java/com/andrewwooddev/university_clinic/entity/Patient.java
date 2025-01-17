package com.andrewwooddev.university_clinic.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "patients")
public class Patient implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "First name is required")
  private String firstName;

  @NotBlank(message = "Last name is required")
  private String lastName;

  @NotBlank(message = "Email is required")
  @Column(unique = true)
  private String email;

  private String phone;

  @NotBlank(message = "Password is required")
  private String password;

  private String role = "p";

  @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Appointment> appointments = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (role.equals("a")) {
      return List.of(new SimpleGrantedAuthority("a"));
    }
    return List.of(new SimpleGrantedAuthority("p"));

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
    return "Patient{" +
        "id=" + id +
        ", first=" + firstName +
        ", last=" + lastName +
        ", email=" + email + '\'' +
        '}';
  }
}
