package com.example.data.datasource.user.repositoryimpl.delete

import com.example.data.datasource.user.remote.delete.DeleteUserDataSource
import com.example.domain.repository.user.delete.DeleteUserRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class DeleteRepositoryImpl @Inject constructor(
    private val dataSource: DeleteUserDataSource
): DeleteUserRepository {
    override suspend fun delete(pk: Int): ResponseBody? {
        return dataSource.delete(pk)
    }
}