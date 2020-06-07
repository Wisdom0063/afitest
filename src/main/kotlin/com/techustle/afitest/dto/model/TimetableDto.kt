package  com.techustle.afitest.dto.model

import java.time.LocalDate
import java.time.LocalTime

data class  TimetableDto(val id:Long, val employeeId:Long, val rate:Double, val project:String, val date: LocalDate, val startTime:LocalTime, val endTime:LocalTime){


}