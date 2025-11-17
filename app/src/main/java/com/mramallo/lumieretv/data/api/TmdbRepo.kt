package com.mramallo.lumieretv.data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mramallo.lumieretv.data.Constants.API_KEY
import com.mramallo.lumieretv.data.model.CastResponse
import com.mramallo.lumieretv.data.model.DataModelResponse
import com.mramallo.lumieretv.data.model.DetailResponse

class TmdbRepo(val service: ApiService) {

    val detailData = MutableLiveData<Response<DetailResponse>>()
    val castData = MutableLiveData<Response<CastResponse>>()

    val topRatedMovies = MutableLiveData<Response<DataModelResponse>>()
    val nowPlayingMovies = MutableLiveData<Response<DataModelResponse>>()

    val movieDetail: LiveData<Response<DetailResponse>>
        get() = detailData

    val castDetail: LiveData<Response<CastResponse>>
        get() = castData

    suspend fun getMovieDetails(id: Int) {
        try {
            val result = service.getMovieDetails(id, API_KEY)

            if(result.body() != null) {
                detailData.postValue(Response.Success(result.body()))
            } else {
                detailData.postValue(Response.Error(result.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            detailData.postValue(Response.Error(e.message ?: "Exception ocurred"))
        }
    }

    suspend fun getMovieCast(id: Int) {
        try {
            val result = service.getMovieCast(id, API_KEY)

            if(result.body() != null) {
                castData.postValue(Response.Success(result.body()))
            } else {
                castData.postValue(Response.Error(result.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            castData.postValue(Response.Error(e.message ?: "Exception ocurred"))
        }
    }

    suspend fun getTopRatedMovies() {
        try {
            val result = service.getTopRatedList(API_KEY)

            if(result.body() != null) {
                topRatedMovies.postValue(Response.Success(result.body()))
            } else {
                topRatedMovies.postValue(Response.Error(result.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            topRatedMovies.postValue(Response.Error(e.message ?: "Exception ocurred"))
        }
    }


    suspend fun getNowPlayingMovies() {
        try {
            val result = service.getNowPlayingList(API_KEY)

            if(result.body() != null) {
                nowPlayingMovies.postValue(Response.Success(result.body()))
            } else {
                nowPlayingMovies.postValue(Response.Error(result.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            nowPlayingMovies.postValue(Response.Error(e.message ?: "Exception ocurred"))
        }
    }

}