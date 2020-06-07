package com.techustle.afitest.v1
import com.fasterxml.jackson.databind.ObjectMapper
import com.techustle.afitest.controller.v1.employee.payload.AddLawyerPayload
import com.techustle.afitest.controller.v1.project.payload.AddProjectPayload
import com.techustle.afitest.controller.v1.schedule.payload.AddShedulePayload
import com.techustle.afitest.dto.model.EmployeeDto
import com.techustle.afitest.dto.model.TimetableDto
import com.techustle.afitest.model.Project
import com.techustle.afitest.utils.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScheduleIntegrationTest {
    // bind the above RANDOM_PORT
    @LocalServerPort
    var port:Int=0

    @Autowired
    private val restTemplate  = TestRestTemplate()

    var headers = HttpHeaders()
    var addSchedulePayload = getAddSchedulePayload()


    @BeforeAll
    fun setup() {
        headers.setBearerAuth(generateToken("finance@gmail.com"))
        val mapper = ObjectMapper()


//Add project
        val addProjectPayload = getAddProjectPayload()
        val projectEntity: HttpEntity<AddProjectPayload> = HttpEntity<AddProjectPayload>(addProjectPayload,
                headers)
        val projectResponse = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/projects", port),
                HttpMethod.POST, projectEntity, responseType)
        val project = projectResponse.body?.get("payload")
        val projectData: Project = mapper.convertValue(project, Project::class.java)

        addSchedulePayload.projectId = projectData.id

        // Add Employee

        val addLawyerPayload = getAddLawyerPayload()
        val employeeEntity: HttpEntity<AddLawyerPayload> = HttpEntity<AddLawyerPayload>(addLawyerPayload,
                headers)

        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/employees/law", port),
                HttpMethod.POST, employeeEntity, responseType)
        val user = response.body?.get("payload")
        val userDto: EmployeeDto = mapper.convertValue(user, EmployeeDto::class.java)
        addSchedulePayload.employeeId = userDto.id!!

        // Generate token with "LAWYER" role
        headers.setBearerAuth(generateToken(addLawyerPayload.email))

    }




    @Test
    @Throws(Exception::class)
    @Order(1)
    fun `It should successfully add a schedule`() {
        var newSchedulePayload = addSchedulePayload
        newSchedulePayload.startTime = LocalDateTime.now()
        newSchedulePayload.endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT.minusMinutes(10))
        val entity: HttpEntity<AddShedulePayload> = HttpEntity<AddShedulePayload>(newSchedulePayload,
                headers)
        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/schedules", port),
               HttpMethod.POST, entity, responseType)
       val schedule = response.body?.get("payload")
        println("schedu is ${response.body}")
        val mapper = ModelMapper()
        val actual = response.statusCode
        assertTrue(actual.is2xxSuccessful);


    }


    @Test
    @Order(2)
    @Throws(Exception::class)
    fun `It should return an error if lawyer is occupied at that time`() {
        val entity: HttpEntity<AddShedulePayload> = HttpEntity<AddShedulePayload>(addSchedulePayload,
                headers)
        val response1 = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/schedules", port),
                HttpMethod.POST, entity, responseType)
        val response2 = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/schedules", port),
                HttpMethod.POST, entity, responseType)
        val actual = response2.statusCode
        assertTrue(actual.is4xxClientError);


    }

    @Test
    @Throws(Exception::class)
    @Order(3)
    fun `It should return an error if end time is before start time`() {
        addSchedulePayload.endTime = LocalDateTime.now()
        addSchedulePayload.startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT.minusMinutes(10) )
        val entity: HttpEntity<AddShedulePayload> = HttpEntity<AddShedulePayload>(addSchedulePayload,
                headers)
        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/schedules", port),
                HttpMethod.POST, entity, responseType)
        val actual = response.statusCode
        assertTrue(actual.is4xxClientError);


    }

    @Test
    @Throws(Exception::class)
    @Order(4)
    fun `It should return an error if end time and start time is not within the week`() {
        var newSchedulePayload = addSchedulePayload
        newSchedulePayload.endTime = LocalDateTime.now().minusDays(10)
        newSchedulePayload.startTime = LocalDateTime.now().minusDays(10).plusHours(1)
        val entity: HttpEntity<AddShedulePayload> = HttpEntity<AddShedulePayload>(newSchedulePayload,
                headers)
        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/schedules", port),
                HttpMethod.POST, entity, responseType)
        val actual = response.statusCode
        assertTrue(actual.is4xxClientError);


    }

    @Test
    @Throws(Exception::class)
    @Order(4)
    fun `It should return an error if end time and start time is not on the same day`() {
        var newSchedulePayload = addSchedulePayload
        newSchedulePayload.endTime = LocalDateTime.now()
        newSchedulePayload.startTime = LocalDateTime.now().plusDays(1)
        val entity: HttpEntity<AddShedulePayload> = HttpEntity<AddShedulePayload>(newSchedulePayload,
                headers)
        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/schedules", port),
                HttpMethod.POST, entity, responseType)
        val actual = response.statusCode
        assertTrue(actual.is4xxClientError);


    }


    @Test
    @Throws(Exception::class)
    @Order(5)
    fun `It should successfully generate timetable`() {
        val entity: HttpEntity<*> = HttpEntity<Any?>(headers)

        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/schedules/employees/timetable/${addSchedulePayload.employeeId}", port),
                HttpMethod.GET, entity, responseType)
        val actual = response.statusCode
        assertTrue(actual.is2xxSuccessful);
    }


    @Test
    @Throws(Exception::class)
    @Order(5)
    fun `It should successfully generate company invoice`() {
        val entity: HttpEntity<*> = HttpEntity<Any?>(headers)

        val response = restTemplate.exchange(CreateUrlWithPort.create("/api/v1/schedules/projects/invoice/${addSchedulePayload.projectId}", port),
                HttpMethod.GET, entity, responseType)
        val actual = response.statusCode
        assertTrue(actual.is2xxSuccessful);
    }


}