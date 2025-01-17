package com.andrewwooddev.university_clinic.service.impl;

import com.andrewwooddev.university_clinic.dto.LoginRequest;
import com.andrewwooddev.university_clinic.dto.Response;
import com.andrewwooddev.university_clinic.dto.DoctorDTO;
import com.andrewwooddev.university_clinic.entity.Doctor;
import com.andrewwooddev.university_clinic.exception.DefaultException;
import com.andrewwooddev.university_clinic.repo.DoctorRepo;
import com.andrewwooddev.university_clinic.repo.AppointmentRepo;
import com.andrewwooddev.university_clinic.service.interfac.IDoctorService;
import com.andrewwooddev.university_clinic.service.AWSS3Service;
import com.andrewwooddev.university_clinic.utils.JWTUtils;
import com.andrewwooddev.university_clinic.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoctorService implements IDoctorService {
  @Autowired
  private DoctorRepo doctorRepo;
  @Autowired
  private AppointmentRepo appointmentRepo;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JWTUtils jwtUtils;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private AWSS3Service awss3Service;


  @Override
  public List<String> getAllDoctorTypes() {
    return doctorRepo.findAllSpecialties();
  }

  @Override
  public Response updateDoctor(Long doctorId, String firstName, String lastName, String email, String phone, BigDecimal rate, String description, MultipartFile photo) {
    return null;
  }

  @Override
  public Response getAvailableDoctorsByTimeAndSpeciality(LocalDateTime startTime, LocalDateTime endTime, String speciality) {
    Response response = new Response();

    try {
      List<Doctor> doctorList = doctorRepo.findAvailableDocsByDatesAndSpecialities(startTime, endTime, speciality);
      List<DoctorDTO> doctorDTOList = Utils.docListEntityToDTO(doctorList);
      response.setStatusCode(200);
      response.setMessage("successful");
      response.setDoctorList(doctorDTOList);

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());
    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error fetching doctor by dates and specialty " + e.getMessage());
    }
    return response;

  }

  @Override
  public Response getAllAvailableDoctors() {
    Response response = new Response();

    try {
      List<Doctor> doctorList = doctorRepo.getAllAvailableDocs();
      List<DoctorDTO> doctorDTOList = Utils.docListEntityToDTO(doctorList);
      response.setStatusCode(200);
      response.setMessage("successful");
      response.setDoctorList(doctorDTOList);

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());
    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error getting all available doctors " + e.getMessage());
    }
    return response;
    
  }

  @Override
  public Response register(Doctor doctor) {
    Response response = new Response();
    try {
      if (doctor.getRole() == null || doctor.getRole().isBlank()) {
        doctor.setRole("p");
      }
      if (doctorRepo.existsByEmail(doctor.getEmail())) {
        throw new DefaultException(doctor.getEmail() + "Already Exists");
      }
      doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
      Doctor savedDoctor = doctorRepo.save(doctor);
      DoctorDTO doctorDTO = Utils.doctorEntityToDTO(savedDoctor);
      response.setStatusCode(200);
      response.setDoctor(doctorDTO);
    } catch (DefaultException e) {
      response.setStatusCode(400);
      response.setMessage(e.getMessage());
    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error Occurred During Doctor Registration " + e.getMessage());

    }
    return response;
  }

  @Override
  public Response login(LoginRequest loginRequest) {

    Response response = new Response();

    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail()
          , loginRequest.getPassword()));
      Doctor doctor =
          doctorRepo.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new DefaultException(
              "Doctor Not Found"));

      String token = jwtUtils.generateToken(doctor);
      response.setStatusCode(200);
      response.setToken(token);
      response.setRole(doctor.getRole());
      response.setExpirationTime("3 Days");
      response.setMessage("successful");

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error Occurred During Doctor Login " + e.getMessage());
    }

    return response;
  }

  @Override
  public Response getAllDoctors() {

    Response response = new Response();
    try {
      List<Doctor> doctorList = doctorRepo.findAll();
      List<DoctorDTO> doctorDTOList = Utils.docListEntityToDTO(doctorList);
      response.setStatusCode(200);
      response.setMessage("successful");
      response.setDoctorList(doctorDTOList);

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error getting all doctors " + e.getMessage());
    }
    return response;
  }

  @Override
  public Response getDoctorAppointmentHistory(String doctorId) {

    Response response = new Response();

    try {
      Doctor doctor =
          doctorRepo.findById(Long.valueOf(doctorId)).orElseThrow(() -> new DefaultException(
              "Doctor Not Found"));
      DoctorDTO doctorDTO =
          Utils.docToAppointmentPlusPatient(doctor);
      response.setStatusCode(200);
      response.setMessage("successful");
      response.setDoctor(doctorDTO);

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error getting all doctors " + e.getMessage());
    }

    return response;
  }

  @Override
  public Response deleteDoctor(String doctorId) {

    Response response = new Response();

    try {
      doctorRepo.findById(Long.valueOf(doctorId)).orElseThrow(() -> new DefaultException(
          "Doctor Not Found"));
      doctorRepo.deleteById(Long.valueOf(doctorId));
      response.setStatusCode(200);
      response.setMessage("successful");

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error deleting doctors " + e.getMessage());
    }

    return response;
  }

  @Override
  public Response getDoctorById(String doctorId) {

    Response response = new Response();

    try {
      Doctor doctor =
          doctorRepo.findById(Long.valueOf(doctorId)).orElseThrow(() -> new DefaultException(
              "Doctor Not Found"));
      DoctorDTO doctorDTO = Utils.doctorEntityToDTO(doctor);
      response.setStatusCode(200);
      response.setMessage("successful");
      response.setDoctor(doctorDTO);

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error getting all doctors " + e.getMessage());
    }

    return response;
  }

  @Override
  public Response getThisDoctorInfo(String email) {

    Response response = new Response();

    try {
      Doctor doctor = doctorRepo.findByEmail(email).orElseThrow(() -> new DefaultException(
          "Doctor Not Found"));
      DoctorDTO doctorDTO = Utils.doctorEntityToDTO(doctor);
      response.setStatusCode(200);
      response.setMessage("successful");
      response.setDoctor(doctorDTO);

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error getting doctor info" + e.getMessage());
    }

    return response;
  }
}

