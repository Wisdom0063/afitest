package  com.techustle.afitest.repository

import com.techustle.afitest.model.Project
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface  ProjectRepository:JpaRepository<Project, Long>{
    fun findByName(name:String): Optional<Project>

}