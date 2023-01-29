package com.clearintentions.friender.services

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.left
import com.clearintentions.friender.errors.*
import com.clearintentions.friender.models.AppUser
import org.springframework.stereotype.Service

@Service
class LoginService(private val lookupService: UserLookupService) {

    suspend fun verifyLoginInfo(username: String, password: String): Either<ClearIntentionsServerError, AppUser> = either {
        val user = lookupService.lookupUsername(username).bind()
        if (user.password == password) {
            user
        } else {
            PasswordInvalidError().left().bind<AppUser>()
        }
    }
}
