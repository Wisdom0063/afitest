package  com.techustle.afitest.repository

import com.techustle.afitest.model.Project
import org.springframework.data.jpa.repository.JpaRepository

interface  ProjectRepository:JpaRepository<Project, Long>{

}