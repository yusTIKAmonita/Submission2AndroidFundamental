package com.example.mysubmission2.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmission2.data.UserData
import com.example.mysubmission2.databinding.UserListBinding
import com.example.mysubmission2.ui.DetailActivity
import kotlin.collections.ArrayList


lateinit var mcontext: Context

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallBack
    private val listArrayUser = ArrayList<UserData>()

    fun setUser (listUser:ArrayList<UserData>) {
        listArrayUser.clear()
        listArrayUser.addAll(listUser)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, I: Int): ListViewHolder {
        val binding = UserListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
//        mcontext = viewGroup.context
        return ListViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listArrayUser[position])
//        holder.itemView.setOnClickListener() {
//
//
//        }
    }

    override fun getItemCount(): Int = listArrayUser.size

    inner class ListViewHolder(private val binding: UserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserData) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(userData.photoUrl)
                        .apply(RequestOptions().override(80,80))
                        .into(imgPhoto)
                tvItemUsername.text = userData.username
                tvItemName.text = userData.type
                itemView.setOnClickListener{
                    onItemClickCallBack.onItemClicked(userData)
//                val intentDetail = Intent(mcontext, DetailActivity::class.java)
//                intentDetail.putExtra(DetailActivity.EXTRA_DETAIL, userData)
//                intentDetail.putExtra(DetailActivity.EXTRA_FAV, userData)
//                mcontext.startActivity(intentDetail)
                }
            }

        }

    }
    fun setOnItemClickCallBack(onItemClickCallBack:OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: UserData)

    }

}


