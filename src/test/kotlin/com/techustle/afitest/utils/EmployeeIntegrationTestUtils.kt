package  com.techustle.afitest.utils

import com.techustle.afitest.controller.v1.employee.payload.AddFinancePayload
import com.techustle.afitest.controller.v1.employee.payload.AddLawyerPayload

fun getAddLawyerPayload (): AddLawyerPayload {
    return AddLawyerPayload(name = faker.name().fullName(), email = "${faker.name().firstName()}@gmail.com", rate = 10.0)


}



fun getAddFinanceMemberPayload (): AddFinancePayload {
    return AddFinancePayload(name = faker.name().fullName(), email = "${faker.name().firstName()}@gmail.com", password = "123456")


}