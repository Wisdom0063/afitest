package  com.techustle.afitest.service

import com.techustle.afitest.model.Timetable
import org.springframework.stereotype.Service
import java.util.*

@Service
interface  TimetableService{
    fun addTimetable(): Timetable
    fun getTimeTableById(id:Long): Optional<Timetable>
}