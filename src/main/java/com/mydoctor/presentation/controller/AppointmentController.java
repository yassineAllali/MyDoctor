package com.mydoctor.presentation.controller;

import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.application.resource.TimeSlotResource;
import com.mydoctor.application.service.AppointmentService;
import com.mydoctor.application.service.SchedulingService;
import com.mydoctor.presentation.mapper.CommandMapper;
import com.mydoctor.presentation.mapper.ResponseMapper;
import com.mydoctor.presentation.request.create.CreateAppointmentRequest;
import com.mydoctor.presentation.request.ScheduleRequest;
import com.mydoctor.presentation.response.AppointmentResponse;
import com.mydoctor.presentation.response.TimeSlotResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final SchedulingService schedulingService;
    private final CommandMapper commandMapper;
    private final ResponseMapper responseMapper;

    public AppointmentController(AppointmentService appointmentService, SchedulingService schedulingService) {
        this.appointmentService = appointmentService;
        this.schedulingService = schedulingService;
        this.commandMapper = new CommandMapper();
        this.responseMapper = new ResponseMapper();
    }

    @GetMapping("/{id}")
    public AppointmentResponse getAppointment(@PathVariable long id) {
        AppointmentResource appointment = appointmentService.get(id);
        return responseMapper.map(appointment);
    }

    @PostMapping("/med-office/{medicalOfficeId}/doctor/{doctorId}")
    public AppointmentResponse schedule(@PathVariable long medicalOfficeId, @PathVariable long doctorId,
                                        @RequestBody ScheduleRequest scheduleRequest) {

        CreateAppointmentCommand appointmentCommand = commandMapper.map(scheduleRequest.appointment());
        CreatePatientCommand patientCommand = commandMapper.map(scheduleRequest.patient());

        AppointmentResource appointment = schedulingService.schedule(appointmentCommand, patientCommand, medicalOfficeId, doctorId);
        return responseMapper.map(appointment);
    }

    @PostMapping("/med-office/{medicalOfficeId}/doctor/{doctorId}/patient/{patientId}")
    public AppointmentResponse schedule(@PathVariable long medicalOfficeId, @PathVariable long doctorId, @PathVariable long patientId,
                                        @RequestBody CreateAppointmentRequest appointmentRequest) {
        CreateAppointmentCommand appointmentCommand = commandMapper.map(appointmentRequest);
        return responseMapper.map(schedulingService.schedule(appointmentCommand, medicalOfficeId, doctorId, patientId));
    }

    @PutMapping("/{id}/cancel")
    public AppointmentResponse cancelAppointment(@PathVariable long id) {
        return responseMapper.map(appointmentService.cancelAppointment(id));
    }

    @GetMapping("/patient/{patientId}")
    public List<AppointmentResponse> getPatientAppointments(@PathVariable long patientId) {
        return appointmentService.getPatientAppointments(patientId)
                .stream()
                .map(responseMapper::map)
                .toList();
    }

    @GetMapping("/med-office/{medicalOfficeId}/doctor/{doctorId}/available")
    public List<TimeSlotResponse> getAvailableSlots(@PathVariable long medicalOfficeId, @PathVariable long doctorId,
                                                    @RequestParam @Schema(example = "2024-01-01", format = "date") LocalDate date,
                                                    @RequestParam("duration") long durationInMin) {
        List<TimeSlotResource> availableTimeSlots = appointmentService.getAvailableSlots(medicalOfficeId, doctorId, date, Duration.ofMinutes(durationInMin));
        return availableTimeSlots.stream().map(responseMapper::map).toList();
    }

    @GetMapping("/med-office/{medicalOfficeId}/doctor/{doctorId}/available/between")
    public List<TimeSlotResponse> getAvailableSlots(@PathVariable long medicalOfficeId, @PathVariable long doctorId,
                                                    @RequestParam @Schema(example = "2024-01-01", format = "date") LocalDate from,
                                                    @RequestParam @Schema(example = "2024-01-01", format = "date") LocalDate to,
                                                    @RequestParam("duration") long durationInMin) {
        List<TimeSlotResource> availableTimeSlots = appointmentService.getAvailableSlots(medicalOfficeId, doctorId, from, to, Duration.ofMinutes(durationInMin));
        return availableTimeSlots.stream().map(responseMapper::map).toList();
    }


}
