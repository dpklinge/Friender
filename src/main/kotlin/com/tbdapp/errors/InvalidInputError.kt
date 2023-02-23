package com.tbdapp.errors

open class InvalidInputError(vararg inputs: String, message: String = "Invalid inputs (${inputs.joinToString()}) were provided") : TbdAppServerError(message, ErrorCode.INVALID_INPUT)
