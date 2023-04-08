package com.revaldi.githubapp.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.revaldi.githubapp.data.FavoriteUser
import com.revaldi.githubapp.databinding.ActivityFavoriteBinding
import com.revaldi.githubapp.model.User
import com.revaldi.githubapp.ui.detail.DetailUserActivity
import com.revaldi.githubapp.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding:ActivityFavoriteBinding
    private lateinit var adapter:UserAdapter
    private lateinit var viewModel:FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnClickCallback(object: UserAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME,data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID,data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR,data.avatar_url)
                    startActivity(it)
                }
                print("Hello")
            }
        })
        binding.apply {
            rvUserFav.setHasFixedSize(true)
            rvUserFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUserFav.adapter = adapter
        }
        viewModel.getFavoriteUser()?.observe(this,{
            if(it!=null){
                val list = mapOfList(it)
                adapter.setList(list)
            }
        })
    }

    private fun mapOfList(users: List<FavoriteUser>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in users){
            val mapped = User(
                user.login,
                user.id,
                user.avatar_url
            )
            listUser.add(mapped)
        }
        return listUser
    }
}