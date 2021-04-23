package com.example.mysubmission2.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mysubmission2.*
import com.example.mysubmission2.databinding.FavoriteListBinding
import java.util.*
import kotlin.collections.ArrayList

class FavoriteAdapter(var activity: Activity): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var listFavorite = ArrayList<FavoriteData>()
    set(listFavorite) {
        if (listFavorite.size > 0) {
            this.listFavorite.clear()
        }

        this.listFavorite.addAll(listFavorite)

        notifyDataSetChanged()
    }

    fun addItem(favorite: FavoriteData) {
        this.listFavorite.add(favorite)
        notifyItemInserted(this.listFavorite.size -1)
    }

    fun updateItem(position: Int, favorite: FavoriteData) {
        this.listFavorite[position] = favorite
        notifyItemChanged(position, favorite)
    }

    fun removeItem(position: Int) {
        this.listFavorite.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFavorite.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_list, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = this.listFavorite.size

    inner class FavoriteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = FavoriteListBinding.bind(itemView)
        fun bind (favorite: FavoriteData){
            binding. tvItemUsername.text = favorite.username
            binding.tvItemName.text = favorite.name
            binding.tvItemLocation.text = favorite.userLocation
            binding.tvListFavorite.setOnClickListener(CustomOnItemClickListener(adapterPosition, object: CustomOnItemClickListener.OnItemClickCallBack{
                override fun onItemClicked(v: View, position: Int) {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_POSITION, position)
                    intent.putExtra(DetailActivity.EXTRA_NOTE, favorite)
                    activity.startActivityForResult(intent, DetailActivity.REQUEST_UPDATE)
                }
            }))
        }

    }

}
//class FavoriteAdapter(var listFavorite: ArrayList<FavoriteData>): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>(), Filterable {
//
//    private val TAG = FavoriteAdapter::class.java.simpleName
//    private lateinit var onItemClickDetail: OnItemClickCallBack
//    private var filterListFavorite: ArrayList<FavoriteData> = listFavorite
//
//    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
//        this.onItemClickDetail = onItemClickCallBack
//    }
//
//    fun setUserFavorite(item: ArrayList<FavoriteData>){
//        listFavorite.clear()
//        listFavorite.addAll(item)
//        notifyDataSetChanged()
//        Log.d(TAG, "$item")
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
//        val binding = FavoriteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return FavoriteViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
//        holder.bind(filterListFavorite[position])
//    }
//
//    override fun getItemCount(): Int = filterListFavorite.size
//
//    inner class FavoriteViewHolder(private val binding: FavoriteListBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(favoriteData: FavoriteData) {
//            with(binding) {
//                Glide.with(itemView.context)
//                        .load(favoriteData.photoUrl)
//                        .apply(RequestOptions().override(80,80))
//                        .into(imgPhoto)
//                tvItemUsername.text = favoriteData.username
//                tvItemName.text = favoriteData.name
//                tvItemLocation.text = favoriteData.userLocation
//                itemView.setOnClickListener{onItemClickDetail.onItemClicked(favoriteData)}
//            }
//        }
//    }
//
//    override fun getFilter(): Filter = object : Filter() {
//        override fun performFiltering(constraint: CharSequence?): FilterResults {
//            val itemSearch = constraint.toString()
//            filterListFavorite = if (itemSearch.isEmpty()) listFavorite
//            else {
//                val favoriteList = ArrayList<FavoriteData>()
//                for (item in listFavorite) {
//                    val name = item.name
//                            ?.toLowerCase(Locale.ROOT)
//                            ?.contains(itemSearch.toLowerCase(Locale.ROOT))
//                    val username = item.username
//                            ?.toLowerCase(Locale.ROOT)
//                            ?.contains(itemSearch.toLowerCase(Locale.ROOT))
//                    if (name == true || username == true) favoriteList.add(item)
//                }
//                favoriteList
//            }
//            val filterResult = FilterResults()
//            filterResult.values = filterListFavorite
//            return filterResult
//        }
//
//        @Suppress("UNCHECKED_CAST")
//        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//            filterListFavorite = results?.values as ArrayList<FavoriteData>
//            notifyDataSetChanged()
//        }
//    }
//
//    interface OnItemClickCallBack {
//        fun onItemClicked(data: FavoriteData)
//    }
//
//}