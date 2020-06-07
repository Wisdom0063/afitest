/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-03 19:59:33 
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-06-07 20:13:52
 * @Desc: Project service implementation
 */
package com.techustle.afitest.service

import com.techustle.afitest.exception.EntityType.PROJECT
import com.techustle.afitest.exception.ExceptionType.DUPLICATE_ENTITY
import com.techustle.afitest.exception.ExceptionType.ENTITY_NOT_FOUND
import com.techustle.afitest.exception.ThrowCustomException
import com.techustle.afitest.model.Project
import com.techustle.afitest.repository.ProjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProjectServiceImpl(@Autowired private val projectRepository: ProjectRepository) : ProjectService {
    /**
     * <p>Add Project</p>
     * <p>Ensure project is not already existing</p>
     */
    override fun addProject(name: String): Project {
        this.ensureProjectDontExist(name)
        var project: Project = Project(name = name)
       return projectRepository.save(project)
    }
/**
 * Get all projects
 */
    override fun getAllProject(): List<Project> {
      return projectRepository.findAll()
    }


    /**
     * Get project by id
     */

    override fun getProjectById(projectId: Long): Project {
        val optionalProject = projectRepository.findById(projectId)
        if (optionalProject.isPresent) {
            return optionalProject.get()
        } else {
            throw ThrowCustomException.exception(entityType = PROJECT, exceptionType = ENTITY_NOT_FOUND)
        }
    }


    /**
     * Ensure project does not exist
     */

    override fun ensureProjectDontExist(name: String) {
        val optionalProject = projectRepository.findByName(name)
        if (optionalProject.isPresent) {
            throw ThrowCustomException.exception(DUPLICATE_ENTITY, "project with name  already exist")
        }
    }
}
