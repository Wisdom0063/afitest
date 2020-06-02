/*
 * @Author: Wisdom Kwarteng
 * @Date: 2020-06-01 13:04:58
 * @Last Modified by:   Wisdom Kwarteng
 * @Last Modified time: 2020-06-01 13:04:58
 */
package  com.techustle.afitest.controller.v1.project.payload

import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty

data class AddProjectPayload(
        @field: NotEmpty(message = "Project name is required")
        val name: String
){

}