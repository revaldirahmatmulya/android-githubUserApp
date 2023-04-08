package com.revaldi.githubapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.revaldi.githubapp.data.DatabaseUser
import com.revaldi.githubapp.data.FavoriteUser
import com.revaldi.githubapp.data.FavoriteUserDao
import com.revaldi.githubapp.network.RetrofitClient
import com.revaldi.githubapp.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()
    private var userDao: FavoriteUserDao?
    private var userDb:DatabaseUser?
    init{
        userDb = DatabaseUser.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }
    fun setDetailUser(username:String){
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object: Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                   if(response.isSuccessful){
                       user.postValue(response.body())
                   }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }
    fun getDetailUser() : LiveData<DetailUserResponse>{
        return user
    }
    fun addFavorite(username: String?, id:Int, avatar: String?){
       CoroutineScope(Dispatchers.IO).launch{
           var user = FavoriteUser(username,id,avatar)
           userDao?.Favorited(user)
       }
    }
    suspend fun checkingUser(id:Int) = userDao?.checkUser(id)
    fun removeFavoritedUser(id:Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavorited(id)
        }
    }
}