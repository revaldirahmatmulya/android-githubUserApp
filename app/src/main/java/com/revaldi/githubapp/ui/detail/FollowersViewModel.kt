package com.revaldi.githubapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revaldi.githubapp.network.RetrofitClient
import com.revaldi.githubapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    val followers = MutableLiveData<ArrayList<User>>()

    fun setFollowers(username: String){
        RetrofitClient.apiInstance
            .getUserFollowers(username)
            .enqueue(object: Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if(response.isSuccessful){
                        followers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    t.message?.let { Log.d("Error", it) }
                }

            })
    }
    fun getFollowers(): LiveData<ArrayList<User>>{
        return followers
    }
}