package com.anshabunin.newsapi

import androidx.annotation.IntRange
import com.anshabunin.newsapi.models.Article
import com.anshabunin.newsapi.models.Language
import com.anshabunin.newsapi.models.Response
import com.anshabunin.newsapi.models.SortBy
import com.anshabunin.newsapi.utils.TimeApiKeyInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.Date

/**
 * [API Documentation](https://newsapi.org/docs/get-started)
 */
interface NewsApi {
    /**
     * API Details [here](https://newsapi.org/docs/endpoints/everything)
     */
    @GET("/everything")
    fun everything(
        @Header("X-Api-Key") apiKey: String,
        @Query("q") query: String? = null,
        @Query("from") date: Date? = null,
        @Query("to") to: Date? = null,
        @Query("languages") languages: List<Language>? = null,
        @Query("sortBy") sortBy: SortBy? = null,
        @Query("pageSize") @IntRange(from = 0, to = 100) pageSize: Int? = 100,
        @Query("page") @IntRange(from = 1) page: Int? = 1,
    ): Result<Response<Article>>
}

private fun NewsApi(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient? = null,
    json: Json
): NewsApi {
    return retrofit(baseUrl, apiKey, okHttpClient, json).create()
}

private fun retrofit(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient?,
    json: Json = Json
): Retrofit {
    val jsonConverterFactory = json.asConverterFactory(MediaType.get("application/json"))

    val modifiedOkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(TimeApiKeyInterceptor(apiKey))
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(modifiedOkHttpClient)
        .build()
}


