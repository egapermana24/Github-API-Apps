package com.example.mygithubuser.userdata.localdata.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mygithubuser.userdata.localdata.`object`.FavoritesUser

@Database(entities = [FavoritesUser::class], version = 1, exportSchema = false)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: FavoritesDatabase? = null
        fun getInstance(context: Context): FavoritesDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesDatabase::class.java, "Favorites.db"
                ).build()
            }
    }
}