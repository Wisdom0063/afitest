package com.techustle.afitest.v1
import com.fasterxml.jackson.databind.ObjectMapper
import com.techustle.afitest.controller.v1.employee.payload.AddLawyerPayload
import com.techustle.afitest.controller.v1.project.payload.AddProjectPayload
import com.techustle.afitest.model.Project
import com.techustle.afitest.utils.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.context.ActiveProfiles
import java.util.*
import kotlin.collections.ArrayList


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ProjectIntegrationTest {
    // bind the above RANDOM_PORT
    @LocalServerPort
    var port:Int=0

    @Autowired
    private val restTemplate  = TestRestTemplate()

    var headers = HttpHeaders()






    @Test
    @Throws(Exception::class)
    fun `It should successfully add a project`() {
        val addLawyerPayload = getAddLawyerPayload()
        val entityLaw: HttpEntity<AddLawyerPayload> = HttpEntity<AddLawyerPayload>(addLawyerPayload,
                headers)

        val responseLaw = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/employees/law", port),
                HttpMethod.POST, entityLaw, responseType)
        headers.setBearerAuth(generateToken(addLawyerPayload.email))
        val addProjectPayload = getAddProjectPayload()
        val entity: HttpEntity<AddProjectPayload> = HttpEntity<AddProjectPayload>(addProjectPayload,
                headers)

        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/projects", port),
                HttpMethod.POST, entity, responseType)
        val project = response.body?.get("payload")
        val mapper = ObjectMapper()
        val projectData: Project = mapper.convertValue(project, Project::class.java)
        val actual = response.statusCode
        assertTrue(actual.is2xxSuccessful);
        assertTrue(projectData.name== addProjectPayload.name)
    }


    @Test
    @Throws(Exception::class)
    fun `It should successfully get get list of products`() {
        val addLawyerPayload = getAddLawyerPayload()
        val entityLaw: HttpEntity<AddLawyerPayload> = HttpEntity<AddLawyerPayload>(addLawyerPayload,
                headers)

        val responseLaw = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/employees/law", port),
                HttpMethod.POST, entityLaw, responseType)
        headers.setBearerAuth(generateToken(addLawyerPayload.email))
        val addProjectPayload = getAddProjectPayload()
        val entity: HttpEntity<*> = HttpEntity<Any?>(headers)

        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/projects", port),
                HttpMethod.GET, entity, responseType)
        val project = response.body?.get("payload")
        val projectType: List<Project> = ArrayList<Project>()
        val mapper = ModelMapper()
        val projectData: List<Project> = listOf(mapper.map(project, Project::class.java))

        println("wisdom is $projectData")

        val actual = response.statusCode
        assertTrue(actual.is2xxSuccessful);
    }

    companion object {
        @AfterAll
        fun deleteUser() {
            println("Done testinggggggggggggggggggggggggg. Hope you are fineeeeeeeeeeeeeeeeeeeeeeeee")
        }

        @BeforeAll
        fun getUserToken(){

        }
    }
}