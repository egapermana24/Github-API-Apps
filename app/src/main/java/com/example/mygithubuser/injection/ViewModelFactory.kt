package com.example.mygithubuser.injection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mygithubuser.userdata.FavoritesUserRepo
import com.example.mygithubuser.viewmodel.DetailsUserGithubViewModel
import com.example.mygithubuser.viewmodel.FavoritesUserViewModel

class ViewModelFactory private constructor(private val favoritesUserRepo: FavoritesUserRepo) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsUserGithubViewModel::class.java)) {
            return DetailsUserGithubViewModel(favoritesUserRepo) as T
        } else if (modelClass.isAssignableFrom(FavoritesUserViewModel::class.java)) {
            return FavoritesUserViewModel(favoritesUserRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}