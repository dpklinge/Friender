package com.clearintentions.ClearIntentionsServer.errors

class InvalidInputError(vararg inputs: String, message: String = "Invalid inputs (${inputs.joinToString()}) were provided"): ClearIntentionsServerError(message, ErrorCode.INVALID_INPUT)
