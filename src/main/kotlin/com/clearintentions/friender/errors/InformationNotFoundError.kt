package com.clearintentions.friender.errors

class InformationNotFoundError(message: String = "Requested information not found") : ClearIntentionsServerError(message, ErrorCode.INFORMATION_NOT_FOUND)
