package  com.techustle.afitest.repository

import com.techustle.afitest.model.BillableRate
import com.techustle.afitest.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface  BillableRateRepository:JpaRepository<BillableRate, Long>{
    @Query("select a from billable_rates a where a.employee = :employee order by a.id desc")
    fun findByEmployee(@Param("employee") employee: Employee): List<BillableRate>

}