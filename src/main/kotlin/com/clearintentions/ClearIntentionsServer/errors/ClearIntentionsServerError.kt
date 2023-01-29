package com.clearintentions.ClearIntentionsServer.errors

import org.springframework.http.HttpStatus
import java.lang.Exception

open class ClearIntentionsServerError(val message: String, val errorCode: ErrorCode)

enum class ErrorCode(val statusCode: HttpStatus){
    INVALID_INPUT(HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST),
    DATABASE_ACCESS_ERROR(HttpStatus.NOT_FOUND)

}