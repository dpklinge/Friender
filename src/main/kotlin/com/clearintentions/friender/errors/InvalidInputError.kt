package com.clearintentions.friender.errors

open class InvalidInputError(vararg inputs: String, message: String = "Invalid inputs (${inputs.joinToString()}) were provided") : ClearIntentionsServerError(message, ErrorCode.INVALID_INPUT)
