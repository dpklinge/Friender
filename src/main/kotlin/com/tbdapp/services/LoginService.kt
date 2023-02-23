package com.tbdapp.services

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.left
import com.tbdapp.errors.*
import com.tbdapp.models.AppUser
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginService(private val lookupService: UserLookupService, private val passwordEncoder: PasswordEncoder) {
    suspend fun verifyLoginInfo(username: String, password: String): Either<TbdAppServerError, AppUser> = either {
        val user = lookupService.lookupUsername(username).bind()
        if (passwordEncoder.matches(password, user.password)) {
            user
        } else {
            PasswordInvalidError().left().bind<AppUser>()
        }
    }
}
