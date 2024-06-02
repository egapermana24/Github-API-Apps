package com.example.mygithubuser.injection

import android.content.Context
import com.example.mygithubuser.userdata.FavoritesUserRepo
import com.example.mygithubuser.userdata.localdata.room.FavoritesDatabase
import com.example.mygithubuser.userdata.network.retrofit.ApiConfig
import com.example.mygithubuser.utility.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoritesUserRepo {
        val apiService = ApiConfig.getApiService()
        val database = FavoritesDatabase.getInstance(context)
        val dao = database.favoritesDao()
        val appExecutors = AppExecutors()
        return FavoritesUserRepo.getInstance(apiService, dao, appExecutors)
    }
}