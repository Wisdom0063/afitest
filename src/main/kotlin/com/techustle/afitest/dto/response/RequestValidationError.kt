/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-03-19 12:24:12 
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-03-19 12:26:13
 * This defines an error structure when a request is rejected
 */
package com.techustle.afitest.dto.response

import com.techustle.afitest.dto.response.SubError
class RequestValidationError : SubError {
    var errorObj: String? = null
    var field: String? = null
    var rejectedValue: Any? = null
    var message: String? = null

    internal constructor(errorObj: String, message: String?) {
        this.errorObj= errorObj
        this.message = message
    }

    internal constructor(errorObj: String, field: String?, rejectedValue: Any?, message: String?) {
        this.errorObj = errorObj
        this.message = message
        this.field = field
        this.rejectedValue = rejectedValue
    }
}