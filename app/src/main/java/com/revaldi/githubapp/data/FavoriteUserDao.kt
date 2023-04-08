package com.revaldi.githubapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert
    suspend fun Favorited(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM favorite_user")
    fun getFavorited(): LiveData<List<FavoriteUser>>

    @Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun removeFavorited(id:Int):Int

    @Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
    suspend fun checkUser(id:Int):Int


}