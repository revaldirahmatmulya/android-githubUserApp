package com.revaldi.githubapp.data
import java.io.Serializable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_user")
data class FavoriteUser(
    val login: String?,
    @PrimaryKey
    val id:Int,
    val avatar_url: String?
): Serializable
