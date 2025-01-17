package com.andrewwooddev.university_clinic.service.impl;

import com.andrewwooddev.university_clinic.dto.AppointmentDTO;
import com.andrewwooddev.university_clinic.dto.Response;
import com.andrewwooddev.university_clinic.entity.Appointment;
import com.andrewwooddev.university_clinic.entity.Doctor;
import com.andrewwooddev.university_clinic.entity.Patient;
import com.andrewwooddev.university_clinic.exception.DefaultException;
import com.andrewwooddev.university_clinic.repo.AppointmentRepo;
import com.andrewwooddev.university_clinic.repo.DoctorRepo;
import com.andrewwooddev.university_clinic.repo.PatientRepo;
import com.andrewwooddev.university_clinic.service.interfac.IAppointmentService;
import com.andrewwooddev.university_clinic.service.interfac.IDoctorService;
import com.andrewwooddev.university_clinic.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService implements IAppointmentService {

  @Autowired
  private AppointmentRepo appointmentRepo;
  @Autowired
  private IDoctorService doctorService;
  @Autowired
  private DoctorRepo doctorRepo;
  @Autowired
  private PatientRepo patientRepo;


  @Override
  public Response saveAppointment(Long doctorId, Long patientId, Appointment appointmentRequest) {

    Response response = new Response();

    try {
      if (appointmentRequest.getEndTime().isBefore(appointmentRequest.getStartTime())) {
        throw new IllegalArgumentException("Appointment start time must be before end time");
      }
      Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new DefaultException(
          "Doctor Not Found"));
      Patient patient = patientRepo.findById(patientId).orElseThrow(() -> new DefaultException(
          "Patient Not Found"));

      List<Appointment> existingAppointments = doctor.getAppointments();

      if (!doctorIsAvailable(appointmentRequest, existingAppointments)) {
        throw new DefaultException("Doctor not available for selected time range");
      }

      appointmentRequest.setDoctor(doctor);
      appointmentRequest.setPatient(patient);
      String appointmentConfirmationCode = Utils.getConfirmationCode(10);
      appointmentRequest.setConfirmationCode(appointmentConfirmationCode);
      appointmentRepo.save(appointmentRequest);
      response.setStatusCode(200);
      response.setMessage("successful");
      response.setConfirmationCode(appointmentConfirmationCode);

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error Saving a appointment: " + e.getMessage());

    }
    return response;
  }


  @Override
  public Response findAppointmentByConfirmationCode(String confirmationCode, String role) {

    Response response = new Response();

    try {
      Appointment appointment =
          appointmentRepo.findByConfirmationCode(confirmationCode).orElseThrow(() -> new DefaultException(
              "Appointment Not Found"));
      AppointmentDTO appointmentDTO;
      switch (role) {
        case "p":
          appointmentDTO = Utils.appointmentToDoc(appointment, true);
          break;
        case "d":
          appointmentDTO = Utils.appointmentToPatient(appointment, true);
          break;
        default:
          appointmentDTO = Utils.appointmentToPatientAndDoc(appointment, true, true);
          break;
      }
      response.setStatusCode(200);
      response.setMessage("successful");
      response.setAppointment(appointmentDTO);

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error Finding a appointment: " + e.getMessage());

    }
    return response;
  }

  @Override
  public Response getAllAppointments() {

    Response response = new Response();

    try {
      List<Appointment> appointmentList = appointmentRepo.findAll(Sort.by(Sort.Direction.DESC,
          "id"));
      List<AppointmentDTO> appointmentDTOList =
          Utils.appointmentListEntityToDTO(appointmentList);
      response.setStatusCode(200);
      response.setMessage("successful");
      response.setAppointmentList(appointmentDTOList);

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error Getting all appointments: " + e.getMessage());

    }
    return response;
  }

  @Override
  public Response cancelAppointment(Long appointmentId) {

    Response response = new Response();

    try {
      appointmentRepo.findById(appointmentId).orElseThrow(() -> new DefaultException("Appointment" +
          " does not exist"));
      appointmentRepo.deleteById(appointmentId);
      response.setStatusCode(200);
      response.setMessage("successful");

    } catch (DefaultException e) {
      response.setStatusCode(404);
      response.setMessage(e.getMessage());

    } catch (Exception e) {
      response.setStatusCode(500);
      response.setMessage("Error Cancelling a appointment: " + e.getMessage());

    }
    return response;
  }


  private boolean doctorIsAvailable(Appointment appointmentRequest,
                                  List<Appointment> existingAppointments) {

    return existingAppointments.stream()
        .noneMatch(existingAppointment ->
            appointmentRequest.getStartTime().equals(existingAppointment.getStartTime())
                || appointmentRequest.getEndTime().isBefore(existingAppointment.getEndTime())

                || (appointmentRequest.getStartTime().isAfter(existingAppointment.getStartTime())
                && appointmentRequest.getStartTime().isBefore(existingAppointment.getEndTime()))

                || (appointmentRequest.getStartTime().isBefore(existingAppointment.getStartTime())
                && appointmentRequest.getEndTime().equals(existingAppointment.getEndTime()))

                || (appointmentRequest.getStartTime().isBefore(existingAppointment.getStartTime())
                && appointmentRequest.getEndTime().isAfter(existingAppointment.getEndTime()))

                || (appointmentRequest.getStartTime().equals(existingAppointment.getEndTime())
                && appointmentRequest.getEndTime().equals(existingAppointment.getStartTime()))

                || (appointmentRequest.getStartTime().equals(existingAppointment.getEndTime())
                && appointmentRequest.getEndTime().equals(appointmentRequest.getStartTime()))
        );
  }
}