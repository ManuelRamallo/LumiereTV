package com.mramallo.lumieretv.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mramallo.lumieretv.data.api.Response
import com.mramallo.lumieretv.data.api.TmdbRepo
import com.mramallo.lumieretv.data.model.DataModelResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(val repo: TmdbRepo) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getNowPlayingMovies()
            repo.getTopRatedMovies()
        }
    }

    val nowPlayingMovies: LiveData<Response<DataModelResponse>>
        get() = repo.nowPlayingMovies

    val topRatedMovies: LiveData<Response<DataModelResponse>>
        get() = repo.topRatedMovies

}