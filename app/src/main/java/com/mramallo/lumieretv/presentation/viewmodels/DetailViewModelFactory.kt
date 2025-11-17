package com.mramallo.lumieretv.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mramallo.lumieretv.data.api.TmdbRepo

class DetailViewModelFactory(val repo: TmdbRepo, val movieId: Int): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(repo, movieId) as T

    }
}