package com.andrewwooddev.university_clinic.service.impl;

import com.andrewwooddev.university_clinic.dto.LoginRequest;
import com.andrewwooddev.university_clinic.dto.Response;
import com.andrewwooddev.university_clinic.dto.PatientDTO;
import com.andrewwooddev.university_clinic.entity.Patient;
import com.andrewwooddev.university_clinic.exception.DefaultException;
import com.andrewwooddev.university_clinic.repo.PatientRepo;
import com.andrewwooddev.university_clinic.service.interfac.IPatientService;
import com.andrewwooddev.university_clinic.utils.JWTUtils;
import com.andrewwooddev.university_clinic.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService implements IPatientService {
  @Autowired
  private PatientRepo patientRepo;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JWTUtils jwtUtils;

  private AuthenticationManager authenticationManager;

  @Autowired
  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }


  @Override
  public Response register(Patient patient) {
    Response response = new Response();
    try {
      if (patient.getRole() == null || patient.getRole().isBlank()) {
        patient.setRole("p");
      }
      if (patientRepo.existsByEmail(patient.getEmail())) {
        throw new DefaultException(patient.getEmail() + " Already Exists");
      }
      patient.setPassword(passwordEncoder.encode(patient.getPassword()));
      Patient savedPatient = patientRepo.save(patient);
      PatientDTO patientDTO = Utils.patientEntityToDTO(savedPatient);
      response.setStatusCode(200);
      response.setPatient(patientDTO);
    } catch (DefaultException e) {
      response.setStatusCode(400);
      response.setMessage(e.getMessage());
    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error Occurred During Patient Registration " + e.getMessage());

    }
    return response;
  }

  @Override
  public Response login(LoginRequest loginRequest) {

    Response response = new Response();

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail()
          , loginRequest.getPassword()));
      Patient patient =
          patientRepo.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new DefaultException(
          "Patient Not Found"));

      String token = jwtUtils.generateToken(patient);
      response.setStatusCode(200);
      response.setToken(token);
      response.setRole(patient.getRole());
      response.setExpirationTime("3 Days");
      response.setMessage("successful");

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error Occurred During Patient Login " + e.getMessage());
    }

    return response;
  }

  @Override
  public Response getAllPatients() {

    Response response = new Response();
    try {
      List<Patient> patientList = patientRepo.findAll();
      List<PatientDTO> patientDTOList = Utils.patientListEntityToDTO(patientList);
      response.setStatusCode(200);
      response.setMessage("successful");
      response.setPatientList(patientDTOList);

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error getting all patients " + e.getMessage());
    }
    return response;
  }

  @Override
  public Response getPatientAppointmentHistory(String patientId) {

    Response response = new Response();

    try {
      Patient patient =
          patientRepo.findById(Long.valueOf(patientId)).orElseThrow(() -> new DefaultException(
          "Patient Not Found"));
      PatientDTO patientDTO =
          Utils.patientToAppointmentPlusDoc(patient);
      response.setStatusCode(200);
      response.setMessage("successful");
      response.setPatient(patientDTO);

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error getting all patients " + e.getMessage());
    }

    return response;
  }

  @Override
  public Response deletePatient(String patientId) {

    Response response = new Response();

    try {
      patientRepo.findById(Long.valueOf(patientId)).orElseThrow(() -> new DefaultException(
          "Patient Not Found"));
      patientRepo.deleteById(Long.valueOf(patientId));
      response.setStatusCode(200);
      response.setMessage("successful");

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error deleting patients " + e.getMessage());
    }

    return response;
  }

  @Override
  public Response getPatientById(String patientId) {

    Response response = new Response();

    try {
      Patient patient =
          patientRepo.findById(Long.valueOf(patientId)).orElseThrow(() -> new DefaultException(
              "Patient Not Found"));
      PatientDTO patientDTO = Utils.patientEntityToDTO(patient);
      response.setStatusCode(200);
      response.setMessage("successful");
      response.setPatient(patientDTO);

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error getting all patients " + e.getMessage());
    }

    return response;
  }

  @Override
  public Response getThisPatientInfo(String email) {

    Response response = new Response();

    try {
      Patient patient = patientRepo.findByEmail(email).orElseThrow(() -> new DefaultException(
          "Patient Not Found"));
      PatientDTO patientDTO = Utils.patientEntityToDTO(patient);
      response.setStatusCode(200);
      response.setMessage("successful");
      response.setPatient(patientDTO);

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error getting patient info" + e.getMessage());
    }

    return response;
  }
}
