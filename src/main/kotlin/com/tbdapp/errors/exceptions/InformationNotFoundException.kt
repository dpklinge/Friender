package com.tbdapp.errors.exceptions

import com.tbdapp.errors.InformationNotFoundError

class InformationNotFoundException(val error: InformationNotFoundError) : Exception(error.message)
