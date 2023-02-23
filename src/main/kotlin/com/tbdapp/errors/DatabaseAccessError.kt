package com.tbdapp.errors

class DatabaseAccessError(message: String = "There was a problem retrieving your information. Please try again.") : TbdAppServerError(message, ErrorCode.DATABASE_ACCESS_ERROR)
