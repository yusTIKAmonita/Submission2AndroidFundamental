package com.example.mysubmission2

import android.view.View
import com.example.mysubmission2.adapter.RecyclerViewAdapter

class CustomOnItemClickListener (private val position: Int, private val onItemClickCallBack: OnItemClickCallBack): View.OnClickListener {
    override fun onClick(v: View) {
        onItemClickCallBack.onItemClicked(v, position)
    }

    interface OnItemClickCallBack {
        fun onItemClicked(v: View, position: Int)
    }
}