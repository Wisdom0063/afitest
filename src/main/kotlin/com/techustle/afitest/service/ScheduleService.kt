/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-01 13:06:16 
 * @Last Modified by:   Wisdom Kwarteng 
 * @Last Modified time: 2020-06-01 13:06:16 
 */
package  com.techustle.afitest.service

import com.techustle.afitest.dto.model.InvoiceDto
import com.techustle.afitest.dto.model.TimetableDto
import com.techustle.afitest.model.Schedule
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import javax.validation.constraints.NotEmpty

@Service
interface  ScheduleService{
    /**
     * Add a schedule
     */
    fun addSchedule(employeeId: Long, projectId: Long, startTime: LocalDateTime, endTime: LocalDateTime): Schedule
    fun getScheduleById(id:Long): Optional<Schedule>
    fun getAllSchedules():List<Schedule>
    fun getEmployeeSchedules(employeeId: Long):List<Schedule>
    fun generateEmployeeTimeTable(employeeId: Long):List<TimetableDto>
    /**
     * 
     */
    fun generateCompanyInvoice(projectId:Long): List<InvoiceDto>
}