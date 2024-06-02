package com.example.mygithubuser.userdata

import androidx.lifecycle.LiveData
import com.example.mygithubuser.userdata.localdata.`object`.FavoritesUser
import com.example.mygithubuser.userdata.localdata.room.FavoriteDao
import com.example.mygithubuser.userdata.network.retrofit.ApiService
import com.example.mygithubuser.utility.AppExecutors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesUserRepo private constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {

    suspend fun insertFavoriteUser(user: FavoritesUser) {
        withContext(Dispatchers.IO) {
            favoriteDao.insert(user)
        }
    }

    suspend fun deleteFavoriteUser(user: FavoritesUser) {
        withContext(Dispatchers.IO) {
            favoriteDao.delete(user)
        }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoritesUser> {
        return favoriteDao.getFavoriteUserByUsername(username)
    }

    fun getFavoriteUsers(): LiveData<List<FavoritesUser>> {
        return favoriteDao.getAllFavoriteUser()
    }

    companion object {
        @Volatile
        private var instance: FavoritesUserRepo? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoritesUserRepo =
            instance ?: synchronized(this) {
                instance ?: FavoritesUserRepo(apiService, newsDao, appExecutors)
            }.also {
                instance = it
            }
    }
}