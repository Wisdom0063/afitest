/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-01 13:06:21 
 * @Last Modified by:   Wisdom Kwarteng 
 * @Last Modified time: 2020-06-01 13:06:21 
 */
package com.techustle.afitest.service

import com.techustle.afitest.dto.model.InvoiceDto
import com.techustle.afitest.dto.model.TimetableDto
import com.techustle.afitest.exception.ExceptionType
import com.techustle.afitest.exception.ThrowCustomException
import com.techustle.afitest.model.Project
import com.techustle.afitest.model.Schedule
import com.techustle.afitest.repository.BillableRateRepository
import com.techustle.afitest.repository.ScheduleRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ScheduleServiceImpl(
    @Autowired private val scheduleRepository: ScheduleRepository,
    @Autowired private val projectService: ProjectService,
    @Autowired private val employeeService: EmployeeService,
    @Autowired private val billableRateRepository: BillableRateRepository
) : ScheduleService {
    /**
     *
     */
    override fun addSchedule(employeeId: Long, projectId: Long, startTime: LocalDateTime, endTime: LocalDateTime): Schedule {

        // get today and clear time of day
        // get today and clear time of day
        val firstDayOfWeekCal = Calendar.getInstance()
        val lastDayOfWeekCal = Calendar.getInstance()

        firstDayOfWeekCal[Calendar.HOUR_OF_DAY] = 0 // ! clear would not reset the hour of day !
        lastDayOfWeekCal[Calendar.HOUR_OF_DAY] = 0 // ! clear would not reset the hour of day !

        firstDayOfWeekCal.clear(Calendar.MINUTE)
        lastDayOfWeekCal.clear(Calendar.MINUTE)

        firstDayOfWeekCal.clear(Calendar.SECOND)
        lastDayOfWeekCal.clear(Calendar.SECOND)

        firstDayOfWeekCal.clear(Calendar.MILLISECOND)
        lastDayOfWeekCal.clear(Calendar.MILLISECOND)

// get start of this week in milliseconds

        firstDayOfWeekCal[Calendar.DAY_OF_WEEK] = firstDayOfWeekCal.firstDayOfWeek
        lastDayOfWeekCal[Calendar.DAY_OF_WEEK] = lastDayOfWeekCal.firstDayOfWeek + 6

        var formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val startDate = formatter.format(Date(firstDayOfWeekCal.timeInMillis))
        val endDate = formatter.format(Date(lastDayOfWeekCal.timeInMillis))

        val startOfWeek: LocalDate = LocalDate.parse(startDate)
        val endOfWeek: LocalDate = LocalDate.parse(endDate)

        if (!startTime.toLocalDate().isBefore(startOfWeek) && !startTime.toLocalDate().isAfter(endOfWeek) && !endTime.toLocalDate().isBefore(startOfWeek) && !endTime.toLocalDate().isAfter(endOfWeek)) {

            if (startTime.isBefore(endTime) && startTime.toLocalDate().isEqual(endTime.toLocalDate())) {

                val project: Project = projectService.getProjectById(projectId)
                val employee = employeeService.findUserById(employeeId)
                val rate = billableRateRepository.findByEmployee(employee)
                if (!rate.isPresent) {
                    throw ThrowCustomException.exception(ExceptionType.BAD_REQUEST, "${employee.name} has no billable rate")
                }

                val employeeSchedules = scheduleRepository.findSchedulesByEmployeeIdAndDate(employeeId, startTime.toLocalDate())
                for (schedule in employeeSchedules) {
                    if (schedule.startTime.isBefore(startTime.toLocalTime()) && schedule.endTime.isAfter(startTime.toLocalTime()) || schedule.startTime.isBefore(endTime.toLocalTime()) && schedule.endTime.isAfter(endTime.toLocalTime()) ||
                            (!schedule.startTime.isBefore(startTime.toLocalTime()) && !schedule.startTime.isAfter(startTime.toLocalTime()) && !schedule.endTime.isBefore(endTime.toLocalTime()) && !schedule.endTime.isAfter(endTime.toLocalTime())) ||
                            (schedule.startTime.isAfter(startTime.toLocalTime()) && schedule.endTime.isBefore(endTime.toLocalTime()))
                    ) {
                        throw ThrowCustomException.exception(ExceptionType.BAD_REQUEST, "${employee.name} is not available within this time")
                    }
                }

                // Add more checks like compared date

                val schedule = Schedule(employee = employee, project = project, billableRate = rate.get(), startTime = startTime.toLocalTime(), endTime = endTime.toLocalTime(), date = startTime.toLocalDate())
                return scheduleRepository.save(schedule)
            } else {
                throw ThrowCustomException.exception(ExceptionType.BAD_REQUEST, "Make sure end time is ahead of start time")
            }
        } else {
            throw ThrowCustomException.exception(ExceptionType.BAD_REQUEST, "Make sure start and end time falls within this week")
        }
    }

    /**
     *
     */
    override fun getScheduleById(id: Long): Optional<Schedule> {
        return scheduleRepository.findById(id)
    }

    /**
     *
     */

    override fun getAllSchedules(): List<Schedule> {
        return scheduleRepository.findAll()
    }

    /**
     *
     */
    override fun getEmployeeSchedules(employeeId: Long): List<Schedule> {
        return scheduleRepository.findSchedulesByEmployeeId(employeeId)
    }

    override fun generateEmployeeTimeTable(employeeId: Long): List<TimetableDto> {
        val employee = employeeService.findUserById(employeeId)
// get today and clear time of day
        // get today and clear time of day
        val cal = Calendar.getInstance()
        cal[Calendar.HOUR_OF_DAY] = 0 // ! clear would not reset the hour of day !

        cal.clear(Calendar.MINUTE)
        cal.clear(Calendar.SECOND)
        cal.clear(Calendar.MILLISECOND)

// get start of this week in milliseconds

        cal[Calendar.DAY_OF_WEEK] = cal.firstDayOfWeek

        var formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        var datefor = formatter.format(Date(cal.timeInMillis))
        val date: LocalDate = LocalDate.parse(datefor)

        val schedules = scheduleRepository.findAllWithDateBefore(employee, date)
        var timetable = ArrayList<TimetableDto>()
        for (schedule in schedules) {
            val timetableDto = TimetableDto(employeeId = schedule.employee.id, rate = schedule.billableRate.rate, project = schedule.project.name, date = schedule.date, startTime = schedule.startTime, endTime = schedule.endTime)
            timetable.add(timetableDto)
        }

        return timetable
    }

    override fun generateCompanyInvoice(projectId: Long): List<InvoiceDto> {
        val project = projectService.getProjectById(projectId)
        val schedules = scheduleRepository.findSchedulesByProjectId(projectId)
        var invoice = ArrayList<InvoiceDto>()
        for (schedule in schedules) {

            val hours = schedule.endTime.hour - schedule.startTime.hour
            val minute = schedule.endTime.minute - schedule.startTime.minute
            val minuteHours = minute.toDouble() / 60
            val totalHours = hours + minuteHours
            val unitPrice = schedule.billableRate.rate
            val cost = unitPrice * totalHours

            var invoiceDto = InvoiceDto(employeeId = schedule.employee.id, numberOfHours = totalHours, unitPrice = unitPrice, cost = cost)
            invoice.add(invoiceDto)
        }
        return invoice
    }
}
