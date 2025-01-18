package com.andrewwooddev.university_clinic.controller;


import com.andrewwooddev.university_clinic.dto.Response;
import com.andrewwooddev.university_clinic.service.interfac.IAppointmentService;
import com.andrewwooddev.university_clinic.service.interfac.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
