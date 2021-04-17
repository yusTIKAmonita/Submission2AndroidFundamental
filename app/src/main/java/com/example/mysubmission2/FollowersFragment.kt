package com.example.mysubmission2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission2.adapter.RecyclerViewAdapter
import com.example.mysubmission2.databinding.FragmentFollowersBinding
import com.example.mysubmission2.viewmodel.FollowViewModel


class FollowersFragment : Fragment() {
    private lateinit var followViewModel: FollowViewModel
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val userFollow = activity?.intent?.getParcelableExtra<UserData>(DetailActivity.EXTRA_DETAIL)
        val tab = activity?.getString(R.string.followers_url)
        adapter = RecyclerViewAdapter()
        adapter.notifyDataSetChanged()

        followViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(activity!!.application))
                .get(FollowViewModel::class.java)

        userFollow?.username?.let {
            if (tab != null) {
                followViewModel.setUserFollow(it, tab)

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
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.adapter = adapter

        adapter.setOnItemClickCallBack(object: RecyclerViewAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: UserData) {
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DETAIL, data)
                startActivity(intent)
            }
        })
    }
    private fun showLoading(state: Boolean) {
        if (state)binding.progressCircular.visibility = View.VISIBLE
        else binding.progressCircular.visibility=View.GONE
    }
}