package com.example.mygithubuser.userdata.localdata.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mygithubuser.userdata.localdata.`object`.FavoritesUser

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: FavoritesUser)

    @Delete
    fun delete(favorite: FavoritesUser)

    @Query("SELECT * from favoritesuser")
    fun getAllFavoriteUser(): LiveData<List<FavoritesUser>>

    @Query("SELECT * FROM favoritesuser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoritesUser>
}