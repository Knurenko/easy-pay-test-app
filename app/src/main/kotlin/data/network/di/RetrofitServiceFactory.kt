package data.network.di

import data.network.EasyPayService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Knurenko Bogdan 28.11.2023
 */
object RetrofitServiceFactory {
    private val retrofit: Retrofit by lazy {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val apiKeyInterceptor = Interceptor { chain ->
            // it seems to be very NOT_PRODUCTION_LIKE key so I decided not to store it in secure way
            val apiKey = "12345"

//            val urlWithHeader =
//                chain.request().url.newBuilder().addQueryParameter("app-key", apiKey).build()
//
//            val newUrl = chain.request().newBuilder().url(urlWithHeader).build()

            val request =
                chain.request().newBuilder()
                    .header("app-key", apiKey)
                    .header("v", "1")
                    .build()

            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()

        Retrofit.Builder()
            .baseUrl("https://easypay.world/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun create(): EasyPayService = retrofit.create(EasyPayService::class.java)
}