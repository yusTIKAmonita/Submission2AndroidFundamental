package com.example.mysubmission2.ui

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission2.MappingHelper
import com.example.mysubmission2.adapter.FavoriteAdapter
import com.example.mysubmission2.adapter.RecyclerViewAdapter
import com.example.mysubmission2.data.FavoriteData
import com.example.mysubmission2.data.UserData
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.CONTENT_URI
//import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.CONTENT_URI
import com.example.mysubmission2.database.FavoriteHelper
import com.example.mysubmission2.databinding.ActivityFavoriteBinding
//import com.example.mysubmission2.viewmodel.FavoriteViewModel
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
        supportActionBar?.title = "Favorite User"

        favoriteAdapter = FavoriteAdapter(this)
        favoriteAdapter.notifyDataSetChanged()


//        if (savedInstanceState == null){
//            loadNoteAsync()
//        } else {
//            val list = savedInstanceState.getParcelableArrayList<FavoriteData>(EXTRA_STATE)
//            if (list != null) {
//                favoriteAdapter.listFavorite = list
//            }
//        }



//        favoriteAdapter.notifyDataSetChanged()
//
//        favoriteViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
//                .get(FavoriteViewModel::class.java).apply {
//                    binding.tvFavorite.text = resources.getString(R.string.loading_data)
//                    showLoading(true)
//                    setUserFavorite(this@FavoriteActivity)
//                    getUserFavorite().observe(this@FavoriteActivity, Observer {
//                        if (it != null) {
//                            showFavoriteItem(it)
//                            Log.d(TAG, "$it")
//                            showLoading(false)
//                        }
//                    }
//                    )
//                }



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

        recylerFavorite()
}

//        @SuppressLint("SetTextI18n")
//        private fun showFavoriteItem(userFavorite: ArrayList<FavoriteData>) {
//            favoriteAdapter.setUserFavorite(userFavorite)
//            if (userFavorite.size >= 1) binding.tvFavorite.text = "${resources.getString(R.string.favored_user)}: ${userFavorite.size}"
//            else if (userFavorite.size == 0) binding.tvFavorite.text = getString(R.string.info_favorite)
//        }

//        private fun showLoading(state: Boolean) {
//            if (state) binding.progresBar.visibility = View.VISIBLE
//            else binding.progresBar.visibility = View.GONE
//
//        }

//    private fun setSelectedUser(data: FavoriteData) {
//        val intent = Intent(this, DetailActivity::class.java)
//        intent.putExtra(DetailActivity.EXTRA_PHOTO, data.photoUrl)
//        intent.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
//        startActivity(intent)


//
//        val mBundle = Bundle().apply {
//            putString(DetailActivity.EXTRA_PHOTO, data.photoUrl)
//            putString(DetailActivity.EXTRA_USERNAME, data.username)
//        }
//
//        NavHostController
//                .findNavController(this)
//                .navigate(R.id.action_favorite, mBundle)
//        closeKeyboard()

//    }
//
//    private fun closeKeyboard() {
//        TODO("Not yet implemented")
//}


        private fun recylerFavorite() {
//        binding.recycleFavorite.setHasFixedSize(true)
            binding.recycleFavorite.layoutManager = LinearLayoutManager(this)
//        favoriteAdapter = FavoriteAdapter(this)
            binding.recycleFavorite.adapter = favoriteAdapter

//        favoriteAdapter.setOnItemClickCallBack(object: FavoriteAdapter.OnItemClickCallBack {
//            override fun onItemClicked(data: FavoriteData) {
//                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
//                intent.putExtra(DetailActivity.EXTRA_DETAIL, data)
//                startActivity(intent)
//            }
//        })
//            favoriteAdapter.setOnItemClickCallBack(object : FavoriteAdapter.OnItemClickCallBack {
//                override fun onItemClicked(data: FavoriteData) = setSelectedUser(data)
//            })
        }


    private fun loadNoteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progresBar.visibility = View.VISIBLE
//            val favoriteHelper = FavoriteHelper.getInstance(applicationContext)
//            favoriteHelper.open()
            val defferedFavorite = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

//            favoriteHelper.close()
            val favDat = defferedFavorite.await()
            binding.progresBar.visibility = View.INVISIBLE
            if (favDat.size > 0) {
                favoriteAdapter.listFavorite = favDat
            } else {
//                binding.progresBar.visibility = View.VISIBLE
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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (data != null) {
//            when(requestCode) {
//                DetailActivity.RESULT_ADD -> if (resultCode == DetailActivity.RESULT_ADD) {
//                    val favorite = data.getParcelableExtra<FavoriteData>(DetailActivity.EXTRA_NOTE) as FavoriteData
//
//                    favoriteAdapter.addItem(favorite)
//                    binding.recycleFavorite.smoothScrollToPosition(favoriteAdapter.itemCount -1)
//
//                    showSnackbarMessage("Data berhasil ditambhakan")
//                }
//
//                DetailActivity.REQUEST_UPDATE ->
//                    when(resultCode) {
//                        DetailActivity.RESULT_DELET -> {
//                            val position = data.getIntExtra(DetailActivity.EXTRA_POSITION, 0)
//
//                            favoriteAdapter.removeItem(position)
//
//                            showSnackbarMessage("Data berhasil dihapus")
//                        }
//                    }
//
//            }
//        }
//    }




    override fun onResume() {
        super.onResume()
        loadNoteAsync()
    }
}



