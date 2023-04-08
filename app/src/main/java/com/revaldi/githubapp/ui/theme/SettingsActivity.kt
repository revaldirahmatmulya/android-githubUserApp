package com.revaldi.githubapp.ui.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import com.revaldi.githubapp.R
import com.revaldi.githubapp.helper.PrefHelper


class SettingsActivity : AppCompatActivity() {
    private val pref by lazy { PrefHelper(this) }
    private lateinit var switch_theme: Switch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        switch_theme = findViewById(R.id.switch_theme)

        if(pref.getBoolean("dark_mode")){
            switch_theme.isChecked = true
        } else {
            switch_theme.isChecked = false
        }
        switch_theme.setOnCheckedChangeListener{compoundButton,isChecked->
            when(isChecked){
                true->{
                    pref.put("dark_mode",true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                false->{
                    pref.put("dark_mode",false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

        }
    }
}