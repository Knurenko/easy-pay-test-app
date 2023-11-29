package data.repo

import data.entity.LoginRequestBody
import data.network.EasyPayService
import domain.repo.AuthRepository

/**
 * @author Knurenko Bogdan 28.11.2023
 */
class AuthRepoImpl(private val service: EasyPayService) : AuthRepository {

    override suspend fun auth(login: String, password: String): String {
        val authBody = LoginRequestBody(login, password)
        val response = service.auth(authBody)

        if (!response.success) {
            throw Exception(response.error?.msg)
        }

        return response.response.token
    }
}