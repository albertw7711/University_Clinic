package com.andrewwooddev.university_clinic.service.interfac;

import com.andrewwooddev.university_clinic.dto.Response;
import com.andrewwooddev.university_clinic.entity.Appointment;

public interface IAppointmentService {

  Response saveAppointment(Long doctorId, Long patientId, Appointment appointmentRequest);

  Response findAppointmentByConfirmationCode(String confirmationCode, String role);

  Response getAllAppointments();

  Response cancelAppointment(Long appointmentId);

}
