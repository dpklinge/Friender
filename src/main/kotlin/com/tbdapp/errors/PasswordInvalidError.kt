package com.tbdapp.errors

class PasswordInvalidError(message: String = "Username or password was invalid") : TbdAppServerError(message, ErrorCode.PASSWORD_INVALID)
