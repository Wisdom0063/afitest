package  com.techustle.afitest.utils

import com.techustle.afitest.model.Employee
import com.techustle.afitest.model.Role
import com.techustle.afitest.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

fun  ensureDateFallsWithinTheWeek(date:LocalDate):Boolean {
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

    return !date.isBefore(startOfWeek) && !date.isAfter(endOfWeek)
}


fun generateHours(startTime:LocalTime, endTime: LocalTime):Double{

    val hours = endTime.hour - startTime.hour
    val minute = endTime.minute - startTime.minute
    val minuteHours = minute.toDouble() / 60
    return hours + minuteHours
}

fun generateFirstDayOfWeek():LocalDate{

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
    return LocalDate.parse(datefor)
}

@Component
class FinanceSeed(@Autowired private  val  employeeRepository: EmployeeRepository, @Autowired private  val passwordEncoder: PasswordEncoder){

    fun insert(){
    val optionaLEmployee = employeeRepository.findByEmail("finance@gmail.com")

        if(!optionaLEmployee.isPresent){
            val employee = Employee(name = "Finance Test", email = "finance@gmail.com", role = Role.FINANCE,  password = passwordEncoder.encode("123456"))
            employeeRepository.save(employee)
        }
    }
}