/*
 * @Author: Wisdom Kwarteng
 * @Date: 2020-03-19 16:12:38
 * @Last Modified by:   Wisdom Kwarteng
 * @Last Modified time: 2020-03-19 16:12:38
 */
package com.techustle.afitest.exception

enum class ExceptionType(var value: String) {
    ENTITY_NOT_FOUND("not.found"), DUPLICATE_ENTITY("duplicate"), WRONG_CREDENTIAL("wrong.credentials"), ENTITY_EXCEPTION("exception"), BAD_REQUEST("bad.request");

}