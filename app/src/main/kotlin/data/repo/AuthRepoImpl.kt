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
        return service.auth(authBody).response.token
    }
}