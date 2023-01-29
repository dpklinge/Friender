package com.clearintentions.ClearIntentionsServer.services

import arrow.core.Either
import arrow.core.Either.Companion.catch
import arrow.core.continuations.either
import arrow.core.left
import com.clearintentions.ClearIntentionsServer.errors.ClearIntentionsServerError
import com.clearintentions.ClearIntentionsServer.errors.PasswordInvalidError
import com.clearintentions.ClearIntentionsServer.errors.DatabaseAccessError
import com.clearintentions.ClearIntentionsServer.errors.InvalidInputError
import com.clearintentions.ClearIntentionsServer.models.Email
import com.clearintentions.ClearIntentionsServer.models.Password
import com.clearintentions.ClearIntentionsServer.models.PhoneNumber
import com.clearintentions.ClearIntentionsServer.models.AppUser
import com.clearintentions.ClearIntentionsServer.repositories.UserRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class LoginService(val userRepository: UserRepository) {
    private suspend fun lookupByEmail(email: Email):  Either<ClearIntentionsServerError, AppUser> = catch{
        userRepository.findAppUserByEmail(email.value)
    }.mapLeft {
        it.printStackTrace()
        DatabaseAccessError()
    }

    private suspend fun lookupByPhone(phoneNumber: PhoneNumber):  Either<ClearIntentionsServerError, AppUser> = catch{
        userRepository.findAppUserByPhoneNumber(phoneNumber.value)
    }.mapLeft {
        it.printStackTrace()
        DatabaseAccessError()
    }
    suspend fun verifyLoginInfo(username: String, password: Password ): Either<ClearIntentionsServerError, AppUser> = either {
        val user = lookupUsername(username).bind()
        if(user.password == password){
            user
        }else {
            PasswordInvalidError().left().bind<AppUser>()
        }
    }

    private suspend fun lookupUsername(username: String): Either<ClearIntentionsServerError, AppUser> = either{
        try {
            val emailLookup = lookupByEmail(Email(username))
            if (emailLookup.isLeft()) {
                lookupByPhone(PhoneNumber(username)).bind()
            } else {
                emailLookup.bind()
            }
        }catch (e: IllegalArgumentException){
            InvalidInputError(inputs = arrayOf(username)).left().bind<AppUser>()
        }
        }
}
