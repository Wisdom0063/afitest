package  com.techustle.afitest.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.*


@Entity(name = "lawyer_schedules")
data class Schedule(
        @Id
        @GeneratedValue(
                strategy = GenerationType.IDENTITY)
        val id: Long=0,
        @OneToOne
        val employee: Employee= Employee(),
        @OneToOne
        val billableRate: BillableRate = BillableRate(),
        @OneToOne
        val project: Project = Project(),
        @Column(nullable=false)
        val date: LocalDate = LocalDate.now(),
        @Column(nullable=false)
        val startTime: LocalTime = LocalTime.now(),
        @Column(nullable=false)
        val endTime: LocalTime = LocalTime.now()
){

}