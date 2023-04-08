package com.revaldi.githubapp.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import com.revaldi.githubapp.data.DatabaseUser
import com.revaldi.githubapp.data.FavoriteUser
import com.revaldi.githubapp.data.FavoriteUserDao


class FavoriteViewModel(application: Application):AndroidViewModel(application) {

    private var userDao: FavoriteUserDao?
    private var userDb: DatabaseUser?
    init{
        userDb = DatabaseUser.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavorited()
    }
}