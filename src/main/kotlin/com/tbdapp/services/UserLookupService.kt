package com.tbdapp.services

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.left
import com.tbdapp.errors.DatabaseAccessError
import com.tbdapp.errors.InformationNotFoundError
import com.tbdapp.errors.InvalidInputError
import com.tbdapp.errors.TbdAppServerError
import com.tbdapp.errors.exceptions.InformationNotFoundException
import com.tbdapp.models.AppUser
import com.tbdapp.models.toStandardizedPhoneNumberFormat
import com.tbdapp.repositories.UserRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class UserLookupService(private val userRepository: UserRepository) {
    private val logger = KotlinLogging.logger {}
    suspend fun lookupByEmail(email: String): Either<TbdAppServerError, AppUser> = Either.catch {
        userRepository.findAppUserByEmail(email)
            ?: throw InformationNotFoundException(InformationNotFoundError("Email $email not located"))
    }.mapLeft {
        handleException(it)
    }

    suspend fun lookupByPhone(phoneNumber: String): Either<TbdAppServerError, AppUser> = Either.catch {
        userRepository.findAppUserByPhoneNumber(phoneNumber) ?: throw InformationNotFoundException(
            InformationNotFoundError("Phone number $phoneNumber not located")
        )
    }.mapLeft {
        handleException(it)
    }

    suspend fun lookupUsername(username: String): Either<TbdAppServerError, AppUser> = either {
        try {
            logger.debug { "Looking up username by email" }
            val emailLookup = lookupByEmail(username)
            if (emailLookup.isLeft()) {
                logger.debug { "Email lookup failed, trying phone number" }
                lookupByPhone(username.toStandardizedPhoneNumberFormat()).bind()
            } else {
                logger.debug { "Email lookup succeeded, returning" }
                emailLookup.bind()
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            InvalidInputError(message = "Invalid username or password", inputs = arrayOf(username)).left().bind<AppUser>()
        }
    }

    private suspend fun handleException(exception: Throwable): TbdAppServerError {
        return if (exception is InformationNotFoundException) {
            logger.debug { "Information not found: ${exception.error.message}" }
            exception.error
        } else {
            logger.warn(exception) { "Error accessing database: " }
            DatabaseAccessError()
        }
    }
}
