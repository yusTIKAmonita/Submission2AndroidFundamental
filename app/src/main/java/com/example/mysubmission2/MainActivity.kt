package com.example.mysubmission2

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission2.adapter.RecyclerViewAdapter
import com.example.mysubmission2.databinding.ActivityMainBinding
import com.example.mysubmission2.viewmodel.MainViewModel
import com.example.mysubmission2.viewmodel.SearchViewModel

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter : RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "User Search"

        adapter = RecyclerViewAdapter()
        adapter.notifyDataSetChanged()

        mainViewModel = ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(MainViewModel::class.java)
        mainViewModel.setUserMain()
        mainViewModel.getUserMain().observe(this, { user ->
            if (user != null) {
                adapter.setUser(user)
                showLoading(false)
            }
        })

        searchViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
                .get(SearchViewModel::class.java)

        showRecyclerView()
    }
    private fun showRecyclerView() {
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = adapter

        adapter.setOnItemClickCallBack(object: RecyclerViewAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: UserData) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DETAIL, data)
                startActivity(intent)
            }
        })
    }

    override fun onCreateOptionsMenu(_menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, _menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = _menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                query.let {
                    searchViewModel.setUserSearch(query)
                    showLoading(true)
                    searchViewModel.getUserSearch().observe(this@MainActivity, {
                        adapter.setUser(it)
                        showLoading(false)
                    })

                }
                val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(window.currentFocus?.windowToken,0)
                return true
            }

            /*
            Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
             */
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }
    private fun showLoading(state: Boolean) {
        if (state)binding.progresBar.visibility = View.VISIBLE
        else binding.progresBar.visibility=View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }

            R.id.action_favorite -> {
                val mIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(mIntent)
            }

            R.id.action_set_reminder-> {
                val mIntent = Intent(this, NotificationActivity::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}