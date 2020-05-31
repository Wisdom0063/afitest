
package  com.techustle.afitest.repository

import com.techustle.afitest.model.Timetable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface  TimetableRepository:JpaRepository<Timetable, Long>{

}