package  com.techustle.afitest.service

import com.techustle.afitest.model.Project
import org.springframework.stereotype.Service
import java.util.*

@Service
interface  ProjectService {
    fun addProject(name:String):Project
    fun getAllProject():List<Project>
    fun getProjectById(projectId:Long):Project
}