package com.clearintentions.friender.errors

class DatabaseAccessError(message: String = "There was a problem retrieving your information. Please try again.") : ClearIntentionsServerError(message, ErrorCode.DATABASE_ACCESS_ERROR)
