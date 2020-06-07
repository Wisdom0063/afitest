package com.techustle.afitest.utils
import com.techustle.afitest.controller.v1.project.payload.AddProjectPayload
import com.techustle.afitest.controller.v1.schedule.payload.AddShedulePayload
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

fun getAddSchedulePayload (): AddShedulePayload {
    return AddShedulePayload(employeeId = 1, projectId = 1, startTime = LocalDateTime.now(), endTime =     LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT.minusMinutes(10))
    )

}