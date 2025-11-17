package com.mramallo.lumieretv.data.api

import com.mramallo.lumieretv.data.model.CastResponse
import com.mramallo.lumieretv.data.model.DataModelResponse
import com.mramallo.lumieretv.data.model.DetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing?language=en-US&page=1")
    suspend fun getNowPlayingList(
        @Query("api_key") apiKey: String
    ): Response<DataModelResponse>

    @GET("movie/top_rated?language=en-US&page=1")
    suspend fun getTopRatedList(
        @Query("api_key") apiKey: String
    ): Response<DataModelResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<DetailResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<CastResponse>

}