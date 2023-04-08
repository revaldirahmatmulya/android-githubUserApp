package com.revaldi.githubapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.revaldi.githubapp.R
import com.revaldi.githubapp.databinding.ActivityMainBinding
import com.revaldi.githubapp.helper.PrefHelper
import com.revaldi.githubapp.model.User
import com.revaldi.githubapp.ui.detail.DetailUserActivity
import com.revaldi.githubapp.ui.favorite.FavoriteActivity
import com.revaldi.githubapp.ui.theme.SettingsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter
    private val pref by lazy { PrefHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()


        adapter.setOnClickCallback(object: UserAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity,DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME,data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID,data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR,data.avatar_url)
                    startActivity(it)
                }
                print("Hello")
            }
        })
        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
        binding.apply {
            rvUserList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUserList.setHasFixedSize(true)
            rvUserList.adapter = adapter

            btnSearch.setOnClickListener{
                searchUser()
            }
            etSearch.setOnKeyListener{
                v,keyCode,event->if(event.action == KeyEvent.ACTION_DOWN&&keyCode == KeyEvent.KEYCODE_ENTER){
                    return@setOnKeyListener true
            }
                return@setOnKeyListener false
            }
        }
        viewModel.getSearchUsers().observe(this,{
            if(it!=null){
                adapter.setList(it as ArrayList<User>)
                progressBarShow(false)
            }
        })

        when(pref.getBoolean("dark_mode")){
            true->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } false-> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }
    private fun progressBarShow(state: Boolean){
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun searchUser(){
        binding.apply {
            val query = etSearch.text.toString()
            if(query.isEmpty()) return
            progressBarShow(true)
            viewModel.setSearchUsers(query)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.fav_menu -> {
                Intent(this,FavoriteActivity::class.java).also{
                    startActivity(it)
                }
            }
            R.id.settings->{
                Intent(this,SettingsActivity::class.java).also{
                    startActivity(it)
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

}