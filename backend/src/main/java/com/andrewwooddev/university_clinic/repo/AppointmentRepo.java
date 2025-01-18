package com.andrewwooddev.university_clinic.repo;

import com.andrewwooddev.university_clinic.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

  Optional<Appointment> findByConfirmationCode(String confirmationCode);
}
