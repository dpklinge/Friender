package com.clearintentions.friender.errors.exceptions

import com.clearintentions.friender.errors.InformationNotFoundError

class InformationNotFoundException(val error: InformationNotFoundError) : Exception(error.message)
