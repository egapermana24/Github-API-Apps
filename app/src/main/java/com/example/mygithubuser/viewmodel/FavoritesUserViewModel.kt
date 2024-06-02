package com.example.mygithubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.userdata.FavoritesUserRepo
import com.example.mygithubuser.userdata.localdata.`object`.FavoritesUser

class FavoritesUserViewModel(favoritesRepository: FavoritesUserRepo) : ViewModel() {

    val favoritesUsers: LiveData<List<FavoritesUser>> = favoritesRepository.getFavoriteUsers()
}
