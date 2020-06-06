package com.techustle.afitest.utils
import com.techustle.afitest.controller.v1.project.payload.AddProjectPayload

fun getAddProjectPayload (): AddProjectPayload {
    return AddProjectPayload(name = faker.company().name())

}