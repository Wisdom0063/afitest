/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-03 19:59:22 
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-06-07 20:15:21
 * @Desc: Interface for project service
 */
package com.techustle.afitest.service

import com.techustle.afitest.model.Project
import java.util.*
import org.springframework.stereotype.Service

@Service
interface ProjectService {
    fun addProject(name: String): Project
    fun getAllProject(): List<Project>
    fun getProjectById(projectId: Long): Project
    fun ensureProjectDontExist(name: String)
}
