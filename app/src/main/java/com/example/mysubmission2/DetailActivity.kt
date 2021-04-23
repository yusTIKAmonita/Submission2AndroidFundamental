package com.example.mysubmission2

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mysubmission2.adapter.DetailSectionPagerAdapter
import com.example.mysubmission2.adapter.FavoriteAdapter
import com.example.mysubmission2.database.DatabaseFavorite
import com.example.mysubmission2.database.FavoriteHelper
import com.example.mysubmission2.databinding.ActivityDetailBinding
import com.example.mysubmission2.databinding.ActivityFavoriteBinding
import com.example.mysubmission2.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding
    private lateinit var detailViewModel: DetailViewModel

    private lateinit var adapter: FavoriteAdapter

    private var stateFavorite = false
    private var favoriteData: FavoriteData? = null
    private var position: Int = 0
    private lateinit var favoriteHelper: FavoriteHelper
    private lateinit var photo: String

    private lateinit var favBinding: ActivityFavoriteBinding


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.follower,
                R.string.following
        )
        const val EXTRA_DETAIL = "extra_detail"

        const val EXTRA_NOTE = "extra note"
        const val EXTRA_POSITION = "extra position"
        const val RESULT_DELET = 301
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = "Detail"

        val user = intent.getParcelableExtra<UserData>(EXTRA_DETAIL) as UserData
        val username = user.username

        detailViewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(application)
        ).get(DetailViewModel::class.java)


        if (username != null) {
            detailViewModel.setUserDetail(username)

        }

        showLoading(true)
        detailViewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding?.apply {
                    detailName.text = it.name
                    detailUsername.text = it.username
                    detailLocation.text = it.userLocation
                    detailRepository.text = it.publicRepos.toString()

                    Glide.with(this@DetailActivity)
                            .load(it.photoUrl)
                            .into(detailPhoto)

                    showLoading(false)
                }
            }
        })

        val sectionsPagerAdapter = DetailSectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tab)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f


        // FAVORITE
        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        favoriteData = intent.getParcelableExtra(EXTRA_NOTE)
        if (favoriteData != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            stateFavorite = true

        } else {
            favoriteData = FavoriteData()
        }

        val actionBarTitle: String
        val btnFav: Int

        if(stateFavorite) {
            actionBarTitle = "Ubah"
            btnFav = R.drawable.ic_baseline_favorite_24

            favoriteData?.let {
                binding?.detailName?.text = favoriteData?.name
                binding?.detailUsername?.text = favoriteData?.username
                binding?.detailLocation?.text = favoriteData?.userLocation
                binding?.detailRepository?.text = favoriteData?.publicRepos.toString()

                Glide.with(this)
                        .load(favoriteData?.photoUrl)
                        .into(binding!!.detailPhoto)

                photo = favoriteData?.photoUrl.toString()
            }
        } else {
            actionBarTitle = "Tambah"
            btnFav = R.drawable.ic_baseline_favorite_24
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding?.btnFavorite?.setImageResource(btnFav)

        binding?.btnFavorite?.setOnClickListener(this)
    }
    private fun showLoading(state: Boolean) {
        if (state)binding?.progressCircular?.visibility = View.VISIBLE
        else binding?.progressCircular?.visibility= View.GONE
    }

    override fun onClick(v: View?) {
        val username = binding?.detailUsername?.text.toString()
        val name = binding?.detailName?.text.toString()
        val location = binding?.detailLocation?.text.toString()
        val repository = binding?.detailUsername?.text.toString()
        val favorite = "1"

        val intent = Intent()
        intent.putExtra(EXTRA_NOTE, favoriteData)
        intent.putExtra(EXTRA_POSITION, position)

        //contentValues untuk menampung data
        val values = ContentValues()
        values.put(DatabaseFavorite.FavColumns.USERNAME, username)
        values.put(DatabaseFavorite.FavColumns.NAME, name)
        values.put(DatabaseFavorite.FavColumns.USER_LOCATION, location)
        values.put(DatabaseFavorite.FavColumns.PUBLIC_REPOS, repository)
        values.put(DatabaseFavorite.FavColumns.FAVORITE, favorite)

        if (stateFavorite){
            val result = favoriteHelper.deleteById(favoriteData?.username.toString()).toLong()
            if(result> 0) {
                intent.putExtra(EXTRA_POSITION, position)
                setResult(RESULT_DELET, intent)
                finish()
            } else {
                Toast.makeText(this, "Gagal menghapus data favorite", Toast.LENGTH_SHORT).show()
            }
        } else {
            val result = favoriteHelper.insert(values)

            if (result > 0) {
                favoriteData?.username = result.toString()
                setResult(RESULT_ADD, intent)
                finish()
            } else {
                Toast.makeText(this, "Gagal menambahkan data favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

//
//class DetailActivity : AppCompatActivity(), View.OnClickListener {
//    private var _binding: ActivityDetailBinding? = null
//    private val binding get() = _binding
//    private lateinit var detailViewModel: DetailViewModel
//
//    private lateinit var image : String
//    private lateinit var usernameFav : String
//    private lateinit var userType : String
//
//    private var isFavorite = false
//    private var favoriteData: FavoriteData? = null
//    private lateinit var favoriteHelper: FavoriteHelper
//    private lateinit var imgPhoto: String
//
//    companion object {
//        @StringRes
//        private val TAB_TITLES = intArrayOf(
//                R.string.follower,
//                R.string.following
//        )
//        const val EXTRA_DETAIL = "extra_detail"
//        const val EXTRA_FAV = "extra_data"
//        const val EXTRA_NOTE = "extra_note"
//        const val EXTRA_POSITION = "extra_position"
//
//        const val EXTRA_PHOTO = "extra_photo"
//        const val EXTRA_USERNAME = "extra_username"
//        const val EXTRA_TYPE = "extra_type"
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        _binding = ActivityDetailBinding.inflate(layoutInflater)
//        setContentView(binding?.root)
//        supportActionBar?.title = "Detail"
//
//        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
//        favoriteHelper.open()
//
//        favoriteData = intent.getParcelableExtra(EXTRA_NOTE)
//        if (favoriteData != null) {
//            setDataFavorite()
//            isFavorite = true
//            val checked: Int = R.drawable.ic_baseline_favorite_24
//            binding?.btnFavorite?.setImageResource(checked)
//        } else {
//            favoriteData = FavoriteData()
//        }
//
//
//        detailViewModel = ViewModelProvider(
//                this,
//                ViewModelProvider.AndroidViewModelFactory(application)
//        ).get(DetailViewModel::class.java)
//
//        val user = intent.getParcelableExtra<UserData>(EXTRA_DETAIL) as UserData
//        val usernameFav = user.username
//
//
//        if (usernameFav != null) {
//            detailViewModel.setUserDetail(usernameFav)
////            checkUserFavorite()
//        }
//
//        showLoading(true)
//        usernameFav?.let { detailViewModel.setUserDetail(it) }
//        detailViewModel.getUserDetail().observe(this, {
//            if (it != null) {
//                binding?.apply {
//                    detailName.text = it.name
//                    detailUsername.text = usernameFav
//                    detailLocation.text = it.userLocation
//                    detailRepository.text = it.publicRepos.toString()
//
//                    Glide.with(this@DetailActivity)
//                            .load(it.photoUrl)
//                            .into(detailPhoto)
//
//                    showLoading(false)
//                    btnFavorite.setOnClickListener(this@DetailActivity)
//                }
//            }
//        })
//
//        val sectionsPagerAdapter = DetailSectionPagerAdapter(this)
//        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
//        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = findViewById(R.id.tab)
//        TabLayoutMediator(tabs, viewPager) { tab, position ->
//            tab.text = resources.getString(TAB_TITLES[position])
//        }.attach()
//    }
//
//    private fun setActionBarTitle(title: String) {
//        if (supportActionBar != null) {
//            this.title = title
//        }
//    }
//
//    private fun showLoading(state: Boolean) {
//        if (state)binding?.progressCircular?.visibility = View.VISIBLE
//        else binding?.progressCircular?.visibility= View.GONE
//    }
//
//    private fun checkUserFavorite() {
//        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
//        if (usernameFav?.let { favoriteHelper.checkId(it) }) {
//            isFavorite = true
//            setStatusFavorite(isFavorite)
//        }
//    }
//
//    private fun addUserFavorite () {
//        val values = ContentValues().apply {
//                    put(DatabaseFavorite.FavColumns.USERNAME, usernameFav)
//                    put(DatabaseFavorite.FavColumns.NAME, detailViewModel.getUserDetail().value?.name)
//                    put(DatabaseFavorite.FavColumns.USER_LOCATION, detailViewModel.getUserDetail().value?.userLocation)
//                    put(DatabaseFavorite.FavColumns.PUBLIC_REPOS, detailViewModel.getUserDetail().value?.publicRepos)
//                    put(DatabaseFavorite.FavColumns.PHOTO_URL, image)
//                }
////                this.contentResolver.insert(CONTENT_URI, values)
////                Toast.makeText(this, "$usernameFav ${getString(R.string.add_favorite)}", Toast.LENGTH_SHORT).show()
//    }
//
//    private fun deletUserFavorite() {
//        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
//        favoriteHelper.open()
//        if(favoriteHelper.checkId(usernameFav)) favoriteHelper.deleteById(usernameFav)
//        Toast.makeText(this, "$usernameFav ${getString(R.string.delet_favorite)}", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.btn_favorite -> {
//                if (!isFavorite) {
//                    addUserFavorite()
//                    isFavorite = !isFavorite
//                    setStatusFavorite(isFavorite)
//                    checkUserFavorite()
//                } else {
//                    deletUserFavorite()
//                    setStatusFavorite(!isFavorite)
//                    isFavorite = false
//                    checkUserFavorite()
//                }
//            }
//
//        }
//    }
//
//    private fun setStatusFavorite (statusFavorite: Boolean) {
//        if (statusFavorite) binding?.btnFavorite?.setImageResource(R.drawable.ic_baseline_favorite_24)
//            else binding?.btnFavorite?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        favoriteHelper.close()
//    }
//}

