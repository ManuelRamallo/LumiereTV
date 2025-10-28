package com.mramallo.lumieretv.data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mramallo.lumieretv.data.Constants.API_KEY
import com.mramallo.lumieretv.data.model.DetailResponse

class TmdbRepo(val service: ApiService) {

    val detailData = MutableLiveData<Response<DetailResponse>>()

    val movieDetail: LiveData<Response<DetailResponse>>
        get() = detailData

    suspend fun getMovieDetails(id: Int) {
        val result = service.getMovieDetails(id, API_KEY)

        try {
            if(result.body() != null) {
                detailData.postValue(Response.Success(result.body()))
            } else {
                detailData.postValue(Response.Error(result.errorBody()?.string() ?: "Unknown error"))
            }
        } catch (e: Exception) {
            detailData.postValue(Response.Error(e.message ?: "Exception ocurred"))
        }
    }

}