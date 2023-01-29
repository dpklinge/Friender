package com.clearintentions.friender.errors

class PasswordInvalidError(message: String = "Username or password was invalid") : ClearIntentionsServerError(message, ErrorCode.PASSWORD_INVALID)
