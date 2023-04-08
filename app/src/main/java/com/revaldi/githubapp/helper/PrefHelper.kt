package com.revaldi.githubapp.helper

import android.content.Context
import android.content.SharedPreferences

class PrefHelper(context: Context) {

    private val PREFS_NAME = "appDarkMode.pref"
    private var sharedPRef: SharedPreferences
    val editor: SharedPreferences.Editor
    init{
        sharedPRef = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
        editor = sharedPRef.edit()
    }
    fun put(key: String,condition:Boolean){
        editor.putBoolean(key,condition).apply()
    }
    fun getBoolean(key: String):Boolean = sharedPRef.getBoolean(key,false)

}