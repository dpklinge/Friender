package com.tbdapp.services

import com.tbdapp.errors.InformationNotFoundError
import com.tbdapp.errors.validation.*
import com.tbdapp.models.AppUser
import com.tbdapp.models.registration.RegistrationInput
import com.tbdapp.models.registration.RegistrationOutcome
import com.tbdapp.models.toStandardizedPhoneNumberFormat
import com.tbdapp.repositories.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitFirstOrNull
import mu.KotlinLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegistrationService(private val userRepository: UserRepository, private val lookupService: UserLookupService, private val passwordEncoder: PasswordEncoder) {
    private val logger = KotlinLogging.logger { }
    suspend fun registerUser(user: RegistrationInput): RegistrationOutcome = coroutineScope {
        logger.debug { "Attempting to register user" }
        var didSucceed = true
        val errorList = mutableListOf<ValidationError>()
        val byEmailDeferred = async { lookupService.lookupByEmail(user.email) }
        val byPhoneNumberDeferred = async { lookupService.lookupByPhone(user.phoneNumber.toStandardizedPhoneNumberFormat()) }
        byEmailDeferred.await()
            .fold(
                {
                    if (it !is InformationNotFoundError) {
                        didSucceed = false
                        errorList.add(ServerError())
                    }
                },
                {
                    didSucceed = false
                    errorList.add(EmailAlreadyExistsError())
                }
            )
        byPhoneNumberDeferred.await().fold(
            {
                if (it !is InformationNotFoundError) {
                    didSucceed = false
                    errorList.add(ServerError())
                }
            },
            {
                didSucceed = false
                errorList.add(PhoneNumberAlreadyExistsError())
            }
        )
        if (user.password != user.passwordConfirmation) {
            didSucceed = false
            errorList.add(NonMatchingPasswordsError())
        }
        if (didSucceed) {
            val storedUser = storeUser(user).awaitFirstOrNull()
            logger.info { "Stored user: $storedUser" }
        }
        return@coroutineScope RegistrationOutcome(didSucceed, errorList)
    }

    private suspend fun storeUser(appUser: RegistrationInput) = userRepository.save(AppUser(appUser.displayName, passwordEncoder.encode(appUser.password), appUser.email, appUser.phoneNumber.toStandardizedPhoneNumberFormat(), appUser.gender, appUser.age))
}
