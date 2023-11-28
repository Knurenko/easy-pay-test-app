package domain.repo

/**
 * @author Knurenko Bogdan 28.11.2023
 */
interface AuthRepository {
    suspend fun auth(login: String, password: String): String
}