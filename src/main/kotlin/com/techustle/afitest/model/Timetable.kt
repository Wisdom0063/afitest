package  com.techustle.afitest.model

import java.time.LocalDateTime
import javax.persistence.*


@Entity(name = "timetables")
data class Timetable(
        @Id
        @GeneratedValue(
                strategy = GenerationType.IDENTITY)
        val id: Long=0,
        @Column(nullable = false)
        val employeeId: Long=0,
        @Column(nullable=false)
        val billableRate: Double =0.0,
        @Column(nullable=false)
        val startTime: LocalDateTime = LocalDateTime.now(),
        @Column(nullable=false)
        val endTime: LocalDateTime = LocalDateTime.now(),
        val role:String=""
){

}