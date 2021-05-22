package com.example.mysubmission2.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission2.R
import com.example.mysubmission2.adapter.RecyclerViewAdapter
import com.example.mysubmission2.data.FavoriteData
import com.example.mysubmission2.data.UserData
import com.example.mysubmission2.databinding.FragmentFollowingBinding
import com.example.mysubmission2.viewmodel.FollowViewModel


class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var followViewModel: FollowViewModel
    private var favorites: FavoriteData? = null
    private lateinit var dataFavorite: FavoriteData
    private lateinit var data : UserData

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_NOTE = "extra_note"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userFollow = activity?.intent?.getParcelableExtra<UserData>(DetailActivity.EXTRA_DETAIL)
        val tab = activity?.getString(R.string.following_url)
        adapter = RecyclerViewAdapter()
        adapter.notifyDataSetChanged()

        followViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(activity!!.application))
            .get(FollowViewModel::class.java)

        userFollow?.username?.let {
            if (tab != null) {
                followViewModel.setUserFollow(it, tab)

            }
        }

//        favorites = activity!!.intent.getParcelableExtra(DetailActivity.EXTRA_NOTE)
//        if (favorites != null) {
//            dataFavorite = (activity!!.intent.getParcelableExtra(FollowersFragment.EXTRA_NOTE) as FavoriteData?)!!
//            followViewModel.getUserFollow()
//        } else {
//            dataFavorite = FavoriteData()
//        }

        val favorite = activity?.intent?.getParcelableExtra<FavoriteData>(DetailActivity.EXTRA_NOTE)
        favorite?.username?.let {
            if (tab !=null) {
                followViewModel.setUserFollow(it.toString(), tab)
            }
        }

        showLoading(true)
        followViewModel.getUserFollow().observe(viewLifecycleOwner, {
            adapter.setUser(it)
            showLoading(false)
        })

        showRecyclerView()

    }
    private fun showRecyclerView() {
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.adapter = adapter

        adapter.setOnItemClickCallBack(object: RecyclerViewAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: UserData) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DETAIL, data)
                startActivity(intent)
            }
        })
    }
    private fun showLoading(state: Boolean) {
        if (state)binding.progressFollow.visibility = View.VISIBLE
        else binding.progressFollow.visibility=View.GONE
    }
}