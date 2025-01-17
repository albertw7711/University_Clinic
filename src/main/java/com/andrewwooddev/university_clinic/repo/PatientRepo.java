package com.andrewwooddev.university_clinic.repo;

import com.andrewwooddev.university_clinic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {
  boolean existsByEmail(String email);

  Optional<Patient> findByEmail(String email);
}
