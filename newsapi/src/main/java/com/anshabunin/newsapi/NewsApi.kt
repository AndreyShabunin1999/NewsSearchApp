package com.anshabunin.newsapi

import androidx.annotation.IntRange
import com.anshabunin.newsapi.models.Language
import com.anshabunin.newsapi.models.SortBy
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface NewsApi {
    @GET("/everything")
    fun everything(
        @Query("q") query: String? = null,
        @Query("from") date: Date? = null,
        @Query("to") to: Date? = null,
        @Query("languages") languages: List<Language>? = null,
        @Query("sortBy") sortBy: SortBy? = null,
        @Query("pageSize") @IntRange(from = 0, to = 100) pageSize: Int? = 100,
        @Query("page") @IntRange(from = 1) page: Int? = 1,
    )
}


