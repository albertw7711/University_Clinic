package com.andrewwooddev.university_clinic.service;

import com.andrewwooddev.university_clinic.exception.DefaultException;
import com.andrewwooddev.university_clinic.repo.DoctorRepo;
import com.andrewwooddev.university_clinic.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private DoctorRepo doctorRepo;

  @Autowired
  private PatientRepo patientRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetails userDetails;
    try {
      userDetails = doctorRepo.findByEmail(username).orElseThrow(() -> new DefaultException(
          "Doctor Not Found"));
    } catch (DefaultException e) {
      userDetails = patientRepo.findByEmail(username).orElseThrow(() -> new DefaultException(
          "Username/Email Not Found"));
    }
    return userDetails;
  }
}
