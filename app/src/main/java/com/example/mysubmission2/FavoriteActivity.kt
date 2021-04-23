package com.example.mysubmission2

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter.EXTRA_STATE
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.HandlerThread
import android.text.BoringLayout
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavAction
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission2.adapter.FavoriteAdapter
import com.example.mysubmission2.database.FavoriteHelper
import com.example.mysubmission2.databinding.ActivityFavoriteBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteAdapter: FavoriteAdapter
//    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var searchView: SearchView
    private lateinit var binding: ActivityFavoriteBinding

    private var listFavorite = ArrayList<FavoriteData>()

    companion object {
//        private  val TAG = FavoriteViewModel::class.java.simpleName
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBarTitle()

        binding.recycleFavorite.setHasFixedSize(true)
        binding.recycleFavorite.layoutManager = LinearLayoutManager(this)
        favoriteAdapter = FavoriteAdapter(this)
        binding.recycleFavorite.adapter = favoriteAdapter

        if (savedInstanceState == null){
            loadNoteAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<FavoriteData>(EXTRA_STATE)
            if (list != null) {
                favoriteAdapter.listFavorite = list
            }
        }


//        favoriteAdapter.notifyDataSetChanged()

//        favoriteViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
//                .get(FavoriteViewModel::class.java).apply {
//                    binding.tvFavorite.text = resources.getString(R.string.loading_data)
//                showLoading(true)
//                setUserFavorite(applicationContext)
//                getUserFavorite().observe(this@FavoriteActivity, Observer {
//                    if (it != null) {
//                        showFavoriteItem(it)
//                        Log.d(TAG, "$it")
//                        showLoading(false)
//                    }
//                }
//                )
//                }
    //        favoriteAdapter.setOnItemClickCallBack(object : FavoriteAdapter.OnItemClickCallBack{
//            override fun onItemClicked(data: FavoriteData) = setSelectedUser(data)
//        })

//
//        val handlerThread = HandlerThread("DataObserver")
//        handlerThread.start()
//        val handler = Handler(handlerThread.looper)
//        val myObserver = object : ContentObserver(handler) {
//            override fun onChange(selfChange: Boolean) {
//                loadNoteAsync()
//            }
//        }

//        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

//        if (savedInstanceState == null) {
//            loadNoteAsync()
//        } else {
//            val _listFavorite = savedInstanceState.getParcelableArrayList<FavoriteData>(EXTRA_STATE)
//            if (_listFavorite != null) {
//                favoriteAdapter.listFavorite = _listFavorite
//            }
//        }
    }

//    @SuppressLint("SetTextI18n")
//    private fun showFavoriteItem(userFavorite: ArrayList<FavoriteData>) {
//        favoriteAdapter.setUserFavorite(userFavorite)
//        if (userFavorite.size >= 1) binding.tvFavorite.text = "${resources.getString(R.string.favored_user)}: ${userFavorite.size}"
//        else if (userFavorite.size == 0) binding.tvFavorite.text = getString(R.string.info_favorite)
//    }

    private fun showLoading(state:Boolean) {
        if (state) binding.progresBar.visibility = View.VISIBLE
        else binding.progresBar.visibility = View.GONE

    }

//    private fun setSelectedUser(data: FavoriteData) {
//        val mBundle = Bundle().apply {
//            putString(DetailActivity.EXTRA_PHOTO, data.photoUrl)
//            putString(DetailActivity.EXTRA_USERNAME, data.username)
//        }
//        NavHostController
//                .findNavController(this)
//                .navigate(R.id.action_favorite, mBundle)
//        closeKeyboard()
//
//    }

//    private fun closeKeyboard() {
//        TODO("Not yet implemented")
//    }

    private fun loadNoteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progresBar.visibility = View.VISIBLE
            val favoriteHelper = FavoriteHelper.getInstance(applicationContext)
            favoriteHelper.open()
            val defferedFavorite = async(Dispatchers.IO) {
                val cursor = favoriteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }

            favoriteHelper.close()
            binding.progresBar.visibility = View.VISIBLE
            val favDat = defferedFavorite.await()
            if (favDat.size > 0) {
                favoriteAdapter.listFavorite = favDat
            } else {
                favoriteAdapter.listFavorite = ArrayList()
                showSnackbarMessage("Tidak ada Data")
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(binding.recycleFavorite, message, Snackbar.LENGTH_SHORT).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            when(requestCode) {
                DetailActivity.RESULT_ADD -> if (resultCode == DetailActivity.RESULT_ADD) {
                    val favorite = data.getParcelableExtra<FavoriteData>(DetailActivity.EXTRA_NOTE) as FavoriteData

                    favoriteAdapter.addItem(favorite)
                    binding.recycleFavorite.smoothScrollToPosition(favoriteAdapter.itemCount -1)

                    showSnackbarMessage("Data berhasil ditambhakan")
                }

                DetailActivity.REQUEST_UPDATE ->
                    when(resultCode) {
                        DetailActivity.RESULT_DELET -> {
                            val position = data.getIntExtra(DetailActivity.EXTRA_POSITION, 0)

                            favoriteAdapter.removeItem(position)

                            showSnackbarMessage("Data berhasil dihapus")
                        }
                    }

            }
        }
    }



//
//    override fun onResume() {
//        super.onResume()
//        loadNoteAsync()
//    }
}