package com.revaldi.githubapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revaldi.githubapp.network.RetrofitClient
import com.revaldi.githubapp.model.User
import com.revaldi.githubapp.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel:ViewModel() {
    val listUsers = MutableLiveData<List<User>>()

    fun setSearchUsers(query:String){
        RetrofitClient.apiInstance
            .getUsers(query)
            .enqueue(object:Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if(response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Error", it) }
                }

            })
    }
    fun getSearchUsers():LiveData<List<User>>{
        return listUsers
    }
}