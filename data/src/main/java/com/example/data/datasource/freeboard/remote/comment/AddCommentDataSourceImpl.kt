package com.example.data.datasource.freeboard.remote.comment

import com.example.data.api.freeboard.AddCommentService
import com.example.data.base.BaseDataSource
import com.example.data.entity.freeboard.request.BaseCommentRequest
import javax.inject.Inject

class AddCommentDataSourceImpl @Inject constructor(
    override val service: AddCommentService
): BaseDataSource<AddCommentService>(), AddCommentDataSource {
    override suspend fun createComment(body: BaseCommentRequest): Int? {
        return safeApiCall {
            service.createComment(body)
        }?.code()
    }
}