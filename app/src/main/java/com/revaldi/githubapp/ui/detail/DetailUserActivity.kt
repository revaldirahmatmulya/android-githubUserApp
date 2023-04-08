package com.revaldi.githubapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.revaldi.githubapp.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = Bundle()
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressBarShow2(true)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID,0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        bundle.putString(EXTRA_USERNAME,username)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        username?.let { viewModel.setDetailUser(it) }
        viewModel.getDetailUser().observe(this,{
            if(it!=null){
                binding.apply {
                    progressBarShow2(false)
                    tvName.text = it.name
                    tvUsername.text = it.login
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(ivProfile)
                    tvFollowers.text = it.followers.toString()
                    tvFollowing.text = it.following.toString()
                }
            }
        })
        val viewPagerAdapter = ViewPagerAdapter(this,supportFragmentManager,bundle)
        binding.apply {
            progressBarShow(true)
            viewPagerFollow.adapter = viewPagerAdapter
            tabs.setupWithViewPager(viewPagerFollow)
            progressBarShow(false)
        }
        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch{
            val count = viewModel.checkingUser(id)
            withContext(Dispatchers.Main){
                if(count != null){
                    if(count>0){
                        binding.imageFavorite.isChecked = true
                        _isChecked = true
                    }else {
                        binding.imageFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }
        binding.imageFavorite.setOnClickListener{
            _isChecked = !_isChecked
            if(_isChecked){
                viewModel.addFavorite(username,id,avatar)

            } else {
                viewModel.removeFavoritedUser(id)
            }
            binding.imageFavorite.isChecked = _isChecked
        }
    }
    private fun progressBarShow(state: Boolean){
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun progressBarShow2(state: Boolean){
        if(state){
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }


}