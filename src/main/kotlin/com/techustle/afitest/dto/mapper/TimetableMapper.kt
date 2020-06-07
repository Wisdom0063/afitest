package  com.techustle.afitest.dto.mapper

// import com.starterkit.springboot.brs.dto.model.user.RoleDto;
import com.techustle.afitest.dto.model.EmployeeDto
import com.techustle.afitest.dto.model.TimetableDto
import com.techustle.afitest.model.Employee
import com.techustle.afitest.model.Schedule
import com.techustle.afitest.repository.BillableRateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TimetableMapper() {
    fun toTimetabelDto(schedule: Schedule): TimetableDto {
        return TimetableDto(id=schedule.id, employeeId = schedule.employee.id, rate = schedule.billableRate.rate, startTime = schedule.startTime, date = schedule.date, endTime = schedule.endTime, project = schedule.project.name)

    }
}
