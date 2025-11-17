package com.mramallo.lumieretv.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mramallo.lumieretv.data.api.TmdbRepo

class HomeViewModelFactory(val repo: TmdbRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }

}