package com.example.data.api.freeboard

import com.example.data.entity.freeboard.request.AddCommentRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddCommentService {
    @POST("/CFR/")
    suspend fun createComment(
        @Body body: AddCommentRequest
    ): Response<Int>
}