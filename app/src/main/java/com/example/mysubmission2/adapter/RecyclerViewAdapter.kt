package com.example.mysubmission2.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmission2.UserData
import com.example.mysubmission2.databinding.UserListBinding
import kotlin.collections.ArrayList



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
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listArrayUser[position])
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
                itemView.setOnClickListener{onItemClickCallBack.onItemClicked(userData)}
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


