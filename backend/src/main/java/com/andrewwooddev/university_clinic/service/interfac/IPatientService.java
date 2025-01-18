package com.andrewwooddev.university_clinic.service.interfac;

import com.andrewwooddev.university_clinic.dto.LoginRequest;
import com.andrewwooddev.university_clinic.dto.Response;
import com.andrewwooddev.university_clinic.entity.Patient;

public interface IPatientService {
  Response register(Patient patient);

  Response login(LoginRequest loginRequest);

  Response getAllPatients();

  Response getPatientAppointmentHistory(String patientId);

  Response deletePatient(String patientId);

  Response getPatientById(String patientId);

  Response getThisPatientInfo(String email);
}
