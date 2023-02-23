package com.tbdapp.errors

class InformationNotFoundError(message: String = "Requested information not found") : TbdAppServerError(message, ErrorCode.INFORMATION_NOT_FOUND)
