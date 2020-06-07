/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-01 13:06:21 
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-06-07 20:12:21
 * @Desc: Schedule service implementation

 */
package com.techustle.afitest.service

import com.techustle.afitest.dto.mapper.TimetableMapper
import com.techustle.afitest.dto.model.InvoiceDto
import com.techustle.afitest.dto.model.TimetableDto
import com.techustle.afitest.exception.ExceptionType
import com.techustle.afitest.exception.ThrowCustomException
import com.techustle.afitest.model.Project
import com.techustle.afitest.model.Schedule
import com.techustle.afitest.repository.BillableRateRepository
import com.techustle.afitest.repository.ScheduleRepository
import com.techustle.afitest.utils.ensureDateFallsWithinTheWeek
import com.techustle.afitest.utils.generateFirstDayOfWeek
import com.techustle.afitest.utils.generateHours
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
     * <p>Add lawyer schedule. It checks if time range is valid, Lawyer is free with that specified time, falls within the week </>
    * <p>It uses the latest billable rate of the lawyer</>
    * <p>It checks if lawyer exist</>
    * <p>It checks if project exist</>

     * @param employeeId
     * @param projectId
     * @param startTime
     * @param endTime

     * @return
     */
    override fun addSchedule(employeeId: Long, projectId: Long, startTime: LocalDateTime, endTime: LocalDateTime): Schedule {

        if (ensureDateFallsWithinTheWeek(startTime.toLocalDate()) && ensureDateFallsWithinTheWeek(endTime.toLocalDate())) {

            if (startTime.isBefore(endTime) && startTime.toLocalDate().isEqual(endTime.toLocalDate())) {

                val project: Project = projectService.getProjectById(projectId)
                val employee = employeeService.findEmployeeById(employeeId)
                val rateList = billableRateRepository.findByEmployee(employee)
                val rate = rateList[0]

                if (rate != null) {

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

                val schedule = Schedule(employee = employee, project = project, billableRate = rate, startTime = startTime.toLocalTime(), endTime = endTime.toLocalTime(), date = startTime.toLocalDate())
                return scheduleRepository.save(schedule)
            } else {
                    throw ThrowCustomException.exception(ExceptionType.BAD_REQUEST, "${employee.name} has no billable rate")
                }
                } else {
                throw ThrowCustomException.exception(ExceptionType.BAD_REQUEST, "Make sure end time is ahead of start time")
            }
        } else {
            throw ThrowCustomException.exception(ExceptionType.BAD_REQUEST, "Make sure start and end time falls within this week")
        }
    }

    /**
     * Get schedule by id
     */
    override fun getScheduleById(id: Long): Optional<Schedule> {
        return scheduleRepository.findById(id)
    }

    /**
     * Get all schedules
     */

    override fun getAllSchedules(): List<Schedule> {
        return scheduleRepository.findAll()
    }

    /**
     * Get employee schedules
     */
    override fun getEmployeeSchedules(employeeId: Long): List<Schedule> {
        return scheduleRepository.findSchedulesByEmployeeId(employeeId)
    }

    override fun generateEmployeeTimeTable(employeeId: Long): List<TimetableDto> {
        val employee = employeeService.findEmployeeById(employeeId)

        val date = generateFirstDayOfWeek()

        val schedules = scheduleRepository.findAllWithDateBefore(employee, date)
        var timetable = ArrayList<TimetableDto>()
        for (schedule in schedules) {
            val timetableDto = TimetableMapper().toTimetabelDto(schedule)
            timetable.add(timetableDto)
        }

        return timetable
    }

    /**
     * <p>Generate Company invoice</p>
     * <p>Generate total hours for each lawyer on the project
     */

    override fun generateCompanyInvoice(projectId: Long): List<InvoiceDto> {
      projectService.getProjectById(projectId)
        val schedules = scheduleRepository.findSchedulesByProjectId(projectId)
        var invoice = ArrayList<InvoiceDto>()
        for (schedule in schedules) {
            val totalHours = generateHours(schedule.startTime, schedule.endTime)
            val unitPrice = schedule.billableRate.rate
            val cost = unitPrice * totalHours

            var invoiceDto = InvoiceDto(employeeId = schedule.employee.id, numberOfHours = totalHours, unitPrice = unitPrice, cost = cost)
            invoice.add(invoiceDto)
        }
        return invoice
    }
}
