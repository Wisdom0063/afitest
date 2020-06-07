/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-06-01 13:04:58 
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-06-07 19:58:08
 */
package com.techustle.afitest.controller.v1.schedule.payload

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

data class AddShedulePayload(
        @field: NotNull(message = "Employee ID is required")
        @JsonProperty("employee_id")
        var employeeId: Long,
        @field: NotNull(message = "Project is required")
        @JsonProperty("project_id")
        var projectId: Long,
        @JsonProperty("start_time")
        var startTime: LocalDateTime,
        @JsonProperty("end_time")
        var endTime: LocalDateTime
)
