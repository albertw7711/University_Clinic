package com.andrewwooddev.university_clinic.utils;

import com.andrewwooddev.university_clinic.dto.AppointmentDTO;
import com.andrewwooddev.university_clinic.dto.DoctorDTO;
import com.andrewwooddev.university_clinic.dto.PatientDTO;
import com.andrewwooddev.university_clinic.entity.Appointment;
import com.andrewwooddev.university_clinic.entity.Doctor;
import com.andrewwooddev.university_clinic.entity.Patient;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

  private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final SecureRandom secureRandom = new SecureRandom();


  public static String getConfirmationCode(int length) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
      char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
      stringBuilder.append(randomChar);
    }
    return stringBuilder.toString();
  }


  public static PatientDTO patientEntityToDTO(Patient patient) {
    PatientDTO patientDTO = new PatientDTO();

    patientDTO.setId(patient.getId());
    patientDTO.setFirstName(patient.getFirstName());
    patientDTO.setLastName(patient.getLastName());
    patientDTO.setEmail(patient.getEmail());
    patientDTO.setPhone(patient.getPhone());
    patientDTO.setRole(patient.getRole());
    return patientDTO;
  }

  public static DoctorDTO doctorEntityToDTO(Doctor doctor) {
    DoctorDTO doctorDTO = new DoctorDTO();

    doctorDTO.setId(doctor.getId());
    doctorDTO.setFirstName(doctor.getFirstName());
    doctorDTO.setLastName(doctor.getLastName());
    doctorDTO.setEmail(doctor.getEmail());
    doctorDTO.setPhone(doctor.getPhone());
    doctorDTO.setRole(doctor.getRole());
    doctorDTO.setRate(doctor.getRate());
    doctorDTO.setPhotoURL(doctor.getPhoto());
    doctorDTO.setDescription(doctor.getDescription());
    return doctorDTO;
  }

  public static AppointmentDTO appointmentEntityToDTO(Appointment appointment) {
    AppointmentDTO appointmentDTO = new AppointmentDTO();

    appointmentDTO.setId(appointment.getId());
    appointmentDTO.setStartTime(appointment.getStartTime());
    appointmentDTO.setEndTime(appointment.getEndTime());
    appointmentDTO.setConfirmationCode(appointment.getConfirmationCode());
    return appointmentDTO;
  }

  public static DoctorDTO doctorToAppointments(Doctor doctor) {
    DoctorDTO doctorDTO = doctorEntityToDTO(doctor);
    if (doctor.getAppointments() != null) {
      doctorDTO.setAppointments(doctor.getAppointments().stream().map(Utils::appointmentEntityToDTO).collect(Collectors.toList()));
    }
    return doctorDTO;
  }

  public static PatientDTO doctorToAppointments(Patient patient) {
    PatientDTO patientDTO = patientEntityToDTO(patient);
    if (patient.getAppointments() != null) {
      patientDTO.setAppointments(patient.getAppointments().stream().map(Utils::appointmentEntityToDTO).collect(Collectors.toList()));
    }
    return patientDTO;
  }

  public static AppointmentDTO appointmentToDoc(Appointment appointment, boolean mapPatient) {

    AppointmentDTO appointmentDTO = appointmentEntityToDTO(appointment);
    if (mapPatient) {
      appointmentDTO.setPatient(Utils.patientEntityToDTO(appointment.getPatient()));
    }
    if (appointment.getDoctor() != null) {
      DoctorDTO doctorDTO = doctorEntityToDTO(appointment.getDoctor());
      appointmentDTO.setDoctor(doctorDTO);
    }
    return appointmentDTO;
  }

  public static AppointmentDTO appointmentToPatient(Appointment appointment, boolean mapDoctor) {

    AppointmentDTO appointmentDTO = appointmentEntityToDTO(appointment);
    if (mapDoctor) {
      appointmentDTO.setDoctor(Utils.doctorEntityToDTO(appointment.getDoctor()));
    }
    if (appointment.getPatient() != null) {
      PatientDTO patientDTO = patientEntityToDTO(appointment.getPatient());
      appointmentDTO.setPatient(patientDTO);
    }
    return appointmentDTO;
  }

  public static AppointmentDTO appointmentToPatientAndDoc(Appointment appointment,
                                                        boolean mapPatient, boolean mapDoctor) {

    AppointmentDTO appointmentDTO = appointmentEntityToDTO(appointment);
    if (mapPatient && appointment.getPatient() != null) {
      appointmentDTO.setPatient(Utils.patientEntityToDTO(appointment.getPatient()));
    }
    if (mapDoctor && appointment.getDoctor() != null) {
      appointmentDTO.setDoctor(Utils.doctorEntityToDTO(appointment.getDoctor()));
    }
    return appointmentDTO;
  }

  public static PatientDTO patientToAppointmentPlusDoc(Patient patient) {
    PatientDTO patientDTO = patientEntityToDTO(patient);
    if (!patient.getAppointments().isEmpty()) {
      patientDTO.setAppointments(patient.getAppointments().stream().map(appointment -> appointmentToDoc(appointment,
          false)).collect(Collectors.toList()));
    }
    return patientDTO;
  }

  public static DoctorDTO docToAppointmentPlusPatient(Doctor doctor) {
    DoctorDTO doctorDTO = doctorEntityToDTO(doctor);
    if (!doctor.getAppointments().isEmpty()) {
      doctorDTO.setAppointments(doctor.getAppointments().stream().map(appointment -> appointmentToPatient(appointment,
          false)).collect(Collectors.toList()));
    }
    return doctorDTO;
  }


  public static List<PatientDTO> patientListEntityToDTO(List<Patient> patientList) {
    return patientList.stream().map(Utils::patientEntityToDTO).collect(Collectors.toList());
  }

  public static List<DoctorDTO> docListEntityToDTO(List<Doctor> docList) {
    return docList.stream().map(Utils::doctorEntityToDTO).collect(Collectors.toList());
  }

  public static List<AppointmentDTO> appointmentListEntityToDTO(List<Appointment> appointmentList) {
    return appointmentList.stream().map(Utils::appointmentEntityToDTO).collect(Collectors.toList());
  }


}
