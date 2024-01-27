package com.mydoctor.presentation.controller;

import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.service.AppointmentService;
import com.mydoctor.application.service.SchedulingService;
import com.mydoctor.presentation.mapper.CommandMapper;
import com.mydoctor.presentation.mapper.ResponseMapper;
import com.mydoctor.presentation.request.CreateAppointmentRequest;
import com.mydoctor.presentation.request.CreatePatientRequest;
import com.mydoctor.presentation.request.ScheduleRequest;
import com.mydoctor.presentation.response.AppointmentResponse;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/med-office/{medicalOfficeId}")
    public AppointmentResponse schedule(@PathVariable long medicalOfficeId,
                                        @RequestBody ScheduleRequest scheduleRequest) {

        CreateAppointmentCommand appointmentCommand = commandMapper.map(scheduleRequest.appointment());
        CreatePatientCommand patientCommand = commandMapper.map(scheduleRequest.patient());

        return responseMapper.map(schedulingService.schedule(appointmentCommand, patientCommand, medicalOfficeId));
    }


}
