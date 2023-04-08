package com.revaldi.githubapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.revaldi.githubapp.R
import com.revaldi.githubapp.databinding.FragmentFollowBinding
import com.revaldi.githubapp.ui.main.UserAdapter

class FragmentFollowing:Fragment(R.layout.fragment_follow) {
    private lateinit var  viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String
    private var _binding : FragmentFollowBinding?=null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUserList.setHasFixedSize(true)
            rvUserList.layoutManager = LinearLayoutManager(activity)
            rvUserList.adapter=adapter
        }
        progressBarShow(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModel.setFollowing(username)
        viewModel.getFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                progressBarShow(false)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun progressBarShow(state: Boolean){
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}