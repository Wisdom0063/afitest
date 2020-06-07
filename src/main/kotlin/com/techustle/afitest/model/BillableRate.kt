package  com.techustle.afitest.model

import org.springframework.core.annotation.Order
import java.time.LocalDateTime
import javax.persistence.*


@Entity(name = "billable_rates")
@Order(1)
data class BillableRate(
        @Id
        @GeneratedValue(
                strategy = GenerationType.IDENTITY)
        val id: Long=0,
        @ManyToOne
        val employee: Employee = Employee(),
        @Column(nullable=false)
        val rate: Double =0.0
){

}