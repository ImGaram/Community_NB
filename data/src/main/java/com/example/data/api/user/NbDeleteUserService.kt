package com.example.data.api.user

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface NbDeleteUserService {
    @DELETE("/User/{pk}")
    suspend fun deleteUser(@Path("pk") pk: Int): Response<ResponseBody>
}