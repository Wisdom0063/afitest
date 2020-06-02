/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-01 13:05:59 
 * @Last Modified by:   Wisdom Kwarteng 
 * @Last Modified time: 2020-06-01 13:05:59 
 */

package  com.techustle.afitest.repository

import com.techustle.afitest.model.Employee
import com.techustle.afitest.model.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface  ScheduleRepository:JpaRepository<Schedule, Long>{
    fun findSchedulesByEmployeeId(employeeId:Long): List<Schedule>
    fun findSchedulesByEmployeeIdAndDate(employeeId:Long, date:LocalDate): List<Schedule>
    fun findSchedulesByProjectId(projectId:Long): List<Schedule>

//
//    fun findAllByPublicationTimeBetween(
//            publicationTimeStart: Date?,
//            publicationTimeEnd: Date?): List<Article?>?

    @Query("select a from schedules a where a.date >= :date and a.employee = :employee")
    fun findAllWithDateBefore(  @Param("employee") employee: Employee, @Param("date") date: LocalDate): List<Schedule>


}