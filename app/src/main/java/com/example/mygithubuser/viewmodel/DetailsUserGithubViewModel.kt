package com.example.mygithubuser.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygithubuser.userdata.FavoritesUserRepo
import com.example.mygithubuser.userdata.localdata.`object`.FavoritesUser
import com.example.mygithubuser.userdata.network.response.DetailsUserGithubResponse
import com.example.mygithubuser.userdata.network.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsUserGithubViewModel(private val favoritesUserRepo: FavoritesUserRepo) :
    ViewModel() {

    private val _detailsUser = MutableLiveData<DetailsUserGithubResponse>()
    val detailsUser: LiveData<DetailsUserGithubResponse> = _detailsUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "DetailsUserGithubViewModel"
    }

    fun getDetailsUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailsUserGithubResponse> {
            override fun onResponse(
                call: Call<DetailsUserGithubResponse>,
                response: Response<DetailsUserGithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailsUser.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailsUserGithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun deleteFavorites(favoritesUser: FavoritesUser) {
        viewModelScope.launch {
            favoritesUserRepo.deleteFavoriteUser(favoritesUser)
        }
    }

    fun shareGithubProfile(context: Context, username: String) {
        val githubProfileUrl = "https://github.com/$username"

        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, githubProfileUrl)
        sendIntent.type = "text/plain"

        val shareIntent = Intent.createChooser(sendIntent, "Share Github Profile")
        startActivity(context, shareIntent, null)
    }


    fun saveFavorites(favoritesUser: FavoritesUser) {
        viewModelScope.launch {
            favoritesUserRepo.insertFavoriteUser(favoritesUser)
        }
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoritesUser> {
        return favoritesUserRepo.getFavoriteUserByUsername(username)
    }

}