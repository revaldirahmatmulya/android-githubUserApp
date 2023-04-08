package com.revaldi.githubapp.model

data class DetailUserResponse(
    val id:Int,
    val name:String,
    val login:String,
    val avatar_url:String,
    val following:Int,
    val followers :Int,
    val following_url:String,
    val followers_url :String,
)
