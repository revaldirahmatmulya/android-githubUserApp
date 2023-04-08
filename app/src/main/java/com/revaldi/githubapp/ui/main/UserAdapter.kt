package com.revaldi.githubapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.revaldi.githubapp.databinding.UsersListItemBinding
import com.revaldi.githubapp.model.User

class UserAdapter:RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val list = ArrayList<User>()
    private var onItemCallBack: OnItemClickCallBack? = null
    fun setList(users:ArrayList<User>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    fun setOnClickCallback(onItemCallBack:OnItemClickCallBack){
        this.onItemCallBack = onItemCallBack
    }


    inner class UserViewHolder(val binding: UsersListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.root.setOnClickListener{
                onItemCallBack?.onItemClicked(user)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivUser)
                tvUsername.text = user.login
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       val view = UsersListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data:User)
    }
}