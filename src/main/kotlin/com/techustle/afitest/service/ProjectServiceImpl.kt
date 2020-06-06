package  com.techustle.afitest.service

import com.techustle.afitest.model.Project
import com.techustle.afitest.repository.ProjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.techustle.afitest.exception.ExceptionType.ENTITY_NOT_FOUND
import com.techustle.afitest.exception.EntityType.PROJECT
import com.techustle.afitest.exception.ThrowCustomException


@Service
class  ProjectServiceImpl(@Autowired private  val projectRepository: ProjectRepository):ProjectService{
    override fun addProject(name: String): Project {
        var project:Project = Project(name = name)
       return projectRepository.save(project)
    }

    override fun getAllProject(): List<Project> {
      return  projectRepository.findAll()
    }

    override fun getProjectById(projectId: Long): Project {
        val optionalProject = projectRepository.findById(projectId)
        if(optionalProject.isPresent){
            return  optionalProject.get()

        }else{
            throw ThrowCustomException.exception(entityType = PROJECT, exceptionType = ENTITY_NOT_FOUND);
        }
    }

}