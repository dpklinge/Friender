package com.clearintentions.ClearIntentionsServer.errors

import javax.lang.model.type.ErrorType

class DatabaseAccessError(message: String = "There was a problem retrieving your data from the server. Please doublecheck your information.") : ClearIntentionsServerError(message, ErrorCode.DATABASE_ACCESS_ERROR)