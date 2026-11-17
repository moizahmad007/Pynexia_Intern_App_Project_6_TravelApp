package com.example.travelexplorer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

data class WikiResponse(
    val extract: String?,
    val thumbnail: WikiThumbnail?
)
data class WikiThumbnail(val source: String?)

interface TravelApi {
    @GET("api/rest_v1/page/summary/{title}")
    fun getSummary(@Path("title", encoded = true) title: String): Call<WikiResponse>
}
