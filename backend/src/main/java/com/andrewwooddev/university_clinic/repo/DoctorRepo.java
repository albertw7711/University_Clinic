package com.andrewwooddev.university_clinic.repo;

import com.andrewwooddev.university_clinic.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long>{

  boolean existsByFirstNameAndLastName(String firstName, String lastName);

  boolean existsByEmail(String email);

  Optional<Doctor> findByFirstNameAndLastName(String firstName, String lastName);

  Optional<Doctor> findByEmail(String email);

  @Query("SELECT DISTINCT d.speciality FROM Doctor d")
  List<String> findAllSpecialties();


  @Query("SELECT d FROM Doctor d WHERE d.speciality LIKE %:speciality% AND " +
      "d.id NOT IN (SELECT a.doctor.id FROM Appointment a WHERE" +
      "(a.startTime <= :startTime) AND (a.endTime >= :endTime))")
  List<Doctor> findAvailableDocsByDatesAndSpecialities(LocalDateTime startTime,
                                                     LocalDateTime endTime,
                                           String speciality);


  @Query("SELECT d FROM Doctor d WHERE d.id NOT IN (SELECT a.doctor.id FROM Appointment a)")
  List<Doctor> getAllAvailableDocs();
}
