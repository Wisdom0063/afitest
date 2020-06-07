/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-01 13:05:13 
 * @Last Modified by:   Wisdom Kwarteng 
 * @Last Modified time: 2020-06-01 13:05:13 
 */
package  com.techustle.afitest.controller.v1.schedule

import com.techustle.afitest.controller.v1.schedule.payload.AddShedulePayload
import com.techustle.afitest.dto.mapper.TimetableMapper
import com.techustle.afitest.dto.model.EmployeeDto
import com.techustle.afitest.dto.model.InvoiceDto
import com.techustle.afitest.dto.model.TimetableDto
import com.techustle.afitest.dto.response.Response
import com.techustle.afitest.model.Schedule
import com.techustle.afitest.service.ScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/schedules")
class  ScheduleController(@Autowired private  val  scheduleService: ScheduleService, @Autowired private  val timetableMapper: TimetableMapper){
    @PostMapping("")
    fun addSchedule(@RequestBody @Valid scheduleData: AddShedulePayload):Response<TimetableDto>{
         val schedule: Schedule = scheduleService.addSchedule(employeeId = scheduleData.employeeId, projectId = scheduleData.projectId, startTime = scheduleData.startTime, endTime = scheduleData.endTime)
        return  Response.ok<TimetableDto>().setResponsePayload(timetableMapper.toTimetabelDto(schedule))

    }

    @GetMapping("")
    fun getAllSchedules():Response<List<TimetableDto>>{
        val  schedules: List<Schedule> = scheduleService.getAllSchedules()
        var timetableDtos : MutableList<TimetableDto> = ArrayList<TimetableDto>()

        for (schedule in schedules){
            timetableDtos.add(timetableMapper.toTimetabelDto(schedule))

        }

        return  Response.ok<List<TimetableDto>>().setResponsePayload(timetableDtos)
    }


    @GetMapping("/employees/{id}")
    fun getEmployeeSchedules(@PathVariable(value = "id") employeeId: Long):Response<List<TimetableDto>>{
        val  schedules: List<Schedule> = scheduleService.getEmployeeSchedules(employeeId)

        var timetableDtos : MutableList<TimetableDto> = ArrayList<TimetableDto>()

        for (schedule in schedules){
            timetableDtos.add(timetableMapper.toTimetabelDto(schedule))

        }

        return  Response.ok<List<TimetableDto>>().setResponsePayload(timetableDtos)
        return  Response.ok<List<TimetableDto>>().setResponsePayload(timetableDtos)
    }


    @GetMapping("/employees/timetable/{id}")
    fun getEmployeeTimeTable(@PathVariable(value = "id") employeeId: Long):Response<List<TimetableDto>>{
        val  timetable: List<TimetableDto> = scheduleService.generateEmployeeTimeTable(employeeId)
        return  Response.ok<List<TimetableDto>>().setResponsePayload(timetable)
    }

    @GetMapping("/projects/invoice/{id}")
    fun generateProjectInvoice(@PathVariable(value = "id") projectId: Long):Response<List<InvoiceDto>>{
        val  invoice: List<InvoiceDto> = scheduleService.generateCompanyInvoice(projectId)
        return  Response.ok<List<InvoiceDto>>().setResponsePayload(invoice)
    }



}