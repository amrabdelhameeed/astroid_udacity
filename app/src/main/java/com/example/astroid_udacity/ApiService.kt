package com.example.astroid_udacity
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor { apiKeyInterceptor(it) }
    .build()!!

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.BASE_URL)
    .build()
interface ApiServiceInterface {
    @GET("neo/rest/v1/feed")
    suspend fun getAllAsteroids(
    ): String
    @GET("planetary/apod")
    suspend fun getPicture(@Query("api_key") apiKey: String): String
}
object ApiServiceImpl {
    val retrofitService: ApiServiceInterface by lazy { retrofit.create(ApiServiceInterface::class.java) }
}
private fun apiKeyInterceptor(it: Interceptor.Chain): Response {
    val originalRequest = it.request()
    val originalHttpUrl = originalRequest.url()
    val newHttpUrl = originalHttpUrl.newBuilder()
        .addQueryParameter("api_key", Constants.API_KEY)
        .build()
    val newRequest = originalRequest.newBuilder()
        .url(newHttpUrl)
        .build()
    return it.proceed(newRequest)
}