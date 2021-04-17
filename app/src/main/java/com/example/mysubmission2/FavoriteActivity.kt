package com.example.mysubmission2

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission2.adapter.FavoriteAdapter
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.CONTENT_URI
import com.example.mysubmission2.databinding.ActivityFavoriteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var binding: ActivityFavoriteBinding

    companion object {
        private const val EXTRA_STATE = "EXTRA STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBarTitle()

        binding.recycleFavorite.layoutManager = LinearLayoutManager(this)
        binding.recycleFavorite.setHasFixedSize(true)
        favoriteAdapter = FavoriteAdapter(this)
        binding.recycleFavorite.adapter = favoriteAdapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadNoteAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadNoteAsync()
        } else {
            val _listFavorite = savedInstanceState.getParcelableArrayList<FavoriteData>(EXTRA_STATE)
            if (_listFavorite != null) {
                favoriteAdapter.listFavorite = _listFavorite
            }
        }
    }

    private fun loadNoteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progresBar.visibility = View.VISIBLE
            val defferedFavorite = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI,
                        null,
                        null,
                        null,
                        null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val favDat = defferedFavorite.await()
            binding.progresBar.visibility = View.VISIBLE
            if (favDat.size > 0) {
                favoriteAdapter.listFavorite = favDat
            } else {
                favoriteAdapter.listFavorite = ArrayList()
                showSnackbarMessage()
            }
        }
    }

    private fun showSnackbarMessage() {
        Toast.makeText(this, getString(R.string.empty_favorite), Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favoriteAdapter.listFavorite)
    }

    private fun setActionBarTitle() {
        if (supportActionBar != null) {
            supportActionBar?.title = "Favorite User"
        }
    }

    override fun onResume() {
        super.onResume()
        loadNoteAsync()
    }
}