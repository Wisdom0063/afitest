/*
 * @Author: Wisdom Kwarteng 
 * @Date: 2020-03-19 12:28:07 
 * @Last Modified by: Wisdom Kwarteng
 * @Last Modified time: 2020-03-25 15:51:06
 * This defines a request response
 */
package com.techustle.afitest.dto.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * API response objects
 */


enum class Status {
    OK, BAD_REQUEST, UNAUTHORIZED, VALIDATION_EXCEPTION, EXCEPTION, WRONG_CREDENTIALS, ACCESS_DENIED, NOT_FOUND, DUPLICATE_ENTITY
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class PageMetadata(var size: Int, var totalElements: Long, var totalPages: Int, var number: Int)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class Response<T> {
     lateinit var status: Status
    var payload: T? = null
    var error: Any? = null
    var metadata: Any? = null
    fun setResponseStatus(status: Status): Response<T> {
        this.status = status
        return  this
    }

    fun setResponsePayload(payload: T?): Response<T> {
        this@Response.payload = payload
        return this
    }

    fun setErrors(errors: ResponseError?): Response<T> {
        this@Response.error = errors
        return  this
    }

    // public void addErrorMsgToResponse(String errorMsg, Exception ex) {
    //     ResponseError error = new ResponseError(errorMsg, )
    //             .setDetails(errorMsg)
    //             .setMessage(ex.getMessage())
    //             .setTimestamp(DateUtils.today());
    //     setErrors(error);
    // }

    companion object {
        /**
         * Handle bad request errors
         * @param <T>
         * @return
        </T> */
        fun <T> badRequest(): Response<T> {
            val response: Response<T> = Response()
            response.setResponseStatus(Status.BAD_REQUEST)
            return response
        }

        /**
         * Handle request that are successful
         * @param <T>
         * @return
        </T> */
        fun <T> ok(): Response<T> {
            val response = Response<T>()
            response.setResponseStatus(Status.OK)
            return response
        }

        /**
         * Handle unauthorized request
         * @param <T>
         * @return
        </T> */
        fun <T> unauthorized(): Response<T> {
            val response: Response<T> = Response()
            response.setResponseStatus(Status.UNAUTHORIZED)
            return response
        }

        /**
         * handle request with body that are not valid
         * @param <T>
         * @return
        </T> */
        fun <T> validationException(): Response<T> {
            val response: Response<T> = Response()
            response.setResponseStatus(Status.VALIDATION_EXCEPTION)
            return response
        }

        /**
         * Handle request that has wrong credentials
         * @param <T>
         * @return
        </T> */
        fun <T> wrongCredentials(): Response<T> {
            val response: Response<T> = Response()
            response.setResponseStatus(Status.WRONG_CREDENTIALS)
            return response
        }

        /**
         * Handle errors when a user doesn't have a permission to access certain resources
         * @param <T>
         * @return
        </T> */
        fun <T> accessDenied(): Response<T> {
            val response: Response<T> = Response()
            response.setResponseStatus(Status.ACCESS_DENIED)
            return response
        }

        /**
         * Handle request when unexpected errors occurrs
         * @param <T>
         * @return
        </T> */
        fun <T> exception(): Response<T> {
            val response: Response<T> = Response()
            response.setResponseStatus(Status.EXCEPTION)
            return response
        }

        /**
         * Handle an error when a requested entity is not found
         * @param <T>
         * @return
        </T> */
        fun <T> notFound(): Response<T> {
            val response: Response<T> = Response()
            response.setResponseStatus(Status.NOT_FOUND)
            return response
        }

        /**
         * Handle an error  when a request leads to a duplicate entity
         * @param <T>
         * @return
        </T> */
        fun <T> duplicateEntity(): Response<T> {
            val response: Response<T> = Response()
            response.setResponseStatus(Status.DUPLICATE_ENTITY)
            return response
        }
    }
}