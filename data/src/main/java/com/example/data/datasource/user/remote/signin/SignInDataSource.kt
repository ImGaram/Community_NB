package com.example.data.datasource.user.remote.signin

import com.example.data.entity.user.request.SignInRequest
import com.example.data.entity.user.response.SignInResponse

interface SignInDataSource {
    suspend fun signUp(
        body: SignInRequest
    ): SignInResponse?
}