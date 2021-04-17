package com.example.mysubmission2.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmission2.*
import com.example.mysubmission2.databinding.FavoriteListBinding

class FavoriteAdapter (private val activity: Activity): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var listFavorite = ArrayList<FavoriteData>()
    set(listFavorite) {
        if (listFavorite.size> 0) {
            this.listFavorite.clear()
        }
        this.listFavorite.addAll(listFavorite)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = FavoriteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = this.listFavorite.size

    inner class FavoriteViewHolder(private val binding: FavoriteListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteData: FavoriteData) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(favoriteData.photoUrl)
                        .apply(RequestOptions().override(80,80))
                        .into(imgPhoto)
                tvItemUsername.text = favoriteData.username
                tvItemName.text = favoriteData.name
                tvItemLocation.text = favoriteData.userLocation
                itemView.setOnClickListener(
                    CustomOnItemClickListener (
                            adapterPosition,
                            object : CustomOnItemClickListener.OnItemClickCallBack {
                                override fun onItemClicked(v: View, position: Int) {
                                    val intent = Intent(activity, DetailActivity::class.java)
                                    intent.putExtra(DetailActivity.EXTRA_POSITION, position)
                                    intent.putExtra(DetailActivity.EXTRA_NOTE, favoriteData)
                                    activity.startActivity(intent)
                                }
                            }
                    )
                )
            }

        }


    }

}