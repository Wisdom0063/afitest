/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-07 19:56:43 
 * @Last Modified by:   Wisdom Kwarteng 
 * @Last Modified time: 2020-06-07 19:56:43 
 */
package  com.techustle.afitest.controller.v1.project

import com.techustle.afitest.controller.v1.project.payload.AddProjectPayload
import com.techustle.afitest.controller.v1.schedule.payload.AddShedulePayload
import com.techustle.afitest.dto.response.Response
import com.techustle.afitest.model.Project
import com.techustle.afitest.service.ProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/projects")
class  ProjectController(@Autowired private  val  projectService: ProjectService){
    @PostMapping("")
    fun addProject(@RequestBody @Valid projectData: AddProjectPayload):Response<Project>{
        val project:Project = projectService.addProject(projectData.name)
        return  Response.ok<Project>().setResponsePayload(project)

    }

    @GetMapping("")
    fun  getAllProjects():Response<List<Project>>{
        val projects:List<Project> = projectService.getAllProject()
        return  Response.ok<List<Project>>().setResponsePayload(projects)
    }


}