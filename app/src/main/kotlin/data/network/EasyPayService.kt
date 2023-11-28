package data.network

import data.entity.BaseResponse
import data.entity.LoginRequestBody
import data.entity.LoginResponse
import data.entity.PaymentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @author Knurenko Bogdan 28.11.2023
 */
interface EasyPayService {

    @POST("api-test/login")
    suspend fun auth(@Body body: LoginRequestBody): BaseResponse<LoginResponse>

    @GET("api-test/payments")
    suspend fun fetchPaymentsForUser(@Header("token") userToken: String): BaseResponse<List<PaymentResponse>>
}