package  com.techustle.afitest.repository

import com.techustle.afitest.model.BillableRate
import com.techustle.afitest.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface  BillableRateRepository:JpaRepository<BillableRate, Long>{
    fun findByEmployee(employee: Employee):Optional<BillableRate>

}