package com.example.mysubmission2.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mysubmission2.MappingHelper
import com.example.mysubmission2.R
import com.example.mysubmission2.adapter.DetailSectionPagerAdapter
import com.example.mysubmission2.data.FavoriteData
import com.example.mysubmission2.data.UserData
import com.example.mysubmission2.database.DatabaseFavorite
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.CONTENT_URI
//import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.CONTENT_URI
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.FAVORITE
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.NAME
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.PHOTO_URL
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.PUBLIC_REPOS
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.USERNAME
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.USER_LOCATION
import com.example.mysubmission2.database.FavoriteHelper
import com.example.mysubmission2.databinding.ActivityDetailBinding
import com.example.mysubmission2.viewmodel.DetailViewModel
import com.example.mysubmission2.viewmodel.FavoriteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

//class DetailActivity : AppCompatActivity(), View.OnClickListener {
//    private var _binding: ActivityDetailBinding? = null
//    private val binding get() = _binding
//    private lateinit var detailViewModel: DetailViewModel
//
//    private lateinit var adapter: FavoriteAdapter
//
//    private var stateFavorite = false
//    private var favoriteData: FavoriteData? = null
//    private var position: Int = 0
//    private lateinit var favoriteHelper: FavoriteHelper
//    private lateinit var photo: String
//
//    private lateinit var favBinding: ActivityFavoriteBinding
//
//
//    companion object {
//        @StringRes
//        private val TAB_TITLES = intArrayOf(
//                R.string.follower,
//                R.string.following
//        )
//        const val EXTRA_DETAIL = "extra_detail"
//
//        const val EXTRA_NOTE = "extra note"
//        const val EXTRA_POSITION = "extra position"
//        const val RESULT_DELET = 301
//        const val RESULT_ADD = 101
//        const val REQUEST_UPDATE = 200
//
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        _binding = ActivityDetailBinding.inflate(layoutInflater)
//        setContentView(binding?.root)
//        supportActionBar?.title = "Detail"
//
//        val user = intent.getParcelableExtra<UserData>(EXTRA_DETAIL) as UserData
//        val username = user.username
//
//        detailViewModel = ViewModelProvider(
//                this,
//                ViewModelProvider.AndroidViewModelFactory(application)
//        ).get(DetailViewModel::class.java)
//
//
//        if (username != null) {
//            detailViewModel.setUserDetail(username)
//        }
//
//        showLoading(true)
//        detailViewModel.getUserDetail().observe(this, {
//            if (it != null) {
//                binding?.apply {
//                    detailName.text = it.name
//                    detailUsername.text = it.username
//                    detailLocation.text = it.userLocation
//                    detailRepository.text = it.publicRepos.toString()
//
//                    Glide.with(this@DetailActivity)
//                            .load(it.photoUrl)
//                            .into(detailPhoto)
//
//                    showLoading(false)
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
//        supportActionBar?.elevation = 0f
//
//
//        // FAVORITE
//        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
//        favoriteHelper.open()
//
//        favoriteData = intent.getParcelableExtra(EXTRA_NOTE)
//        if (favoriteData != null) {
//            position = intent.getIntExtra(EXTRA_POSITION, 0)
//            stateFavorite = true
//
//        } else {
//            favoriteData = FavoriteData()
//        }
//
//        val actionBarTitle: String
//        val btnFav: Int
//
//        if(stateFavorite) {
//            actionBarTitle = "Ubah"
//            btnFav = R.drawable.ic_baseline_favorite_24
//
//            favoriteData?.let {
//                binding?.detailName?.text = favoriteData?.name
//                binding?.detailUsername?.text = favoriteData?.username
//                binding?.detailLocation?.text = favoriteData?.userLocation
//                binding?.detailRepository?.text = favoriteData?.publicRepos.toString()
//
//                Glide.with(this)
//                        .load(favoriteData?.photoUrl)
//                        .into(binding!!.detailPhoto)
//
//                photo = favoriteData?.photoUrl.toString()
//            }
//        } else {
//            actionBarTitle = "Tambah"
//            btnFav = R.drawable.ic_baseline_favorite_24
//        }
//
//        supportActionBar?.title = actionBarTitle
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//
//        binding?.btnFavorite?.setImageResource(btnFav)
//
//        binding?.btnFavorite?.setOnClickListener(this)
//    }
//    private fun showLoading(state: Boolean) {
//        if (state)binding?.progressCircular?.visibility = View.VISIBLE
//        else binding?.progressCircular?.visibility= View.GONE
//    }
//
//    override fun onClick(v: View?) {
//        val username = binding?.detailUsername?.text.toString()
//        val name = binding?.detailName?.text.toString()
//        val location = binding?.detailLocation?.text.toString()
//        val repository = binding?.detailUsername?.text.toString()
//        val favorite = "1"
//
//        val intent = Intent()
//        intent.putExtra(EXTRA_NOTE, favoriteData)
//        intent.putExtra(EXTRA_POSITION, position)
//
//        //contentValues untuk menampung data
//        val values = ContentValues()
//        values.put(DatabaseFavorite.FavColumns.USERNAME, username)
//        values.put(DatabaseFavorite.FavColumns.NAME, name)
//        values.put(DatabaseFavorite.FavColumns.USER_LOCATION, location)
//        values.put(DatabaseFavorite.FavColumns.PUBLIC_REPOS, repository)
//        values.put(DatabaseFavorite.FavColumns.FAVORITE, favorite)
//
//        if (stateFavorite){
//            val result = favoriteHelper.deleteById(favoriteData?.username.toString()).toLong()
//            if(result> 0) {
//                intent.putExtra(EXTRA_POSITION, position)
//                setResult(RESULT_DELET, intent)
//                finish()
//            } else {
//                Toast.makeText(this, "Gagal menghapus data favorite", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            val result = favoriteHelper.insert(values)
//
//            if (result > 0) {
//                favoriteData?.username = result.toString()
//                setResult(RESULT_ADD, intent)
//                finish()
//            } else {
//                Toast.makeText(this, "Gagal menambahkan data favorite", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}


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
////        favoriteHelper = FavoriteHelper.getInstance(this)
////        favoriteHelper.open()
//
//
//
//        checkUserFavorite()
//
//        detailViewModel = ViewModelProvider(
//                this,
//                ViewModelProvider.AndroidViewModelFactory(application)
//        ).get(DetailViewModel::class.java)
//
//        val user = intent.getParcelableExtra<UserData>(EXTRA_DETAIL) as UserData
//        val username = user.username
//
//        if (username != null) {
//            detailViewModel.setUserDetail(username)
//        }
//
//        showLoading(true)
//        usernameFav?.let { detailViewModel.setUserDetail(it) }
//        detailViewModel.getUserDetail().observe(this, {
//            if (it != null) {
//                binding?.apply {
//                    detailName.text = it.name
//                    detailUsername.text = it.username
//                    detailLocation.text = it.userLocation
//                    detailRepository.text = it.publicRepos.toString()
//
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
//    private fun getSelecterUser() {
////        val user = intent.getParcelableExtra<UserData>(EXTRA_FAV) as FavoriteData
////        val userFavorite = user.username
////
////        if (userFavorite != null) {
////            image = userFavorite.get(EXTRA_PHOTO).toString()
////            usernameFav = userFavorite.get(EXTRA_USERNAME).toString()
////        }
//
//        if (this !=null)
//            image = this?.get(EXTRA_PHOTO).toString()
//        usernameFav= this.getString(EXTRA_USERNAME)
//    }
//
//    private fun showLoading(state: Boolean) {
//        if (state)binding?.progressCircular?.visibility = View.VISIBLE
//        else binding?.progressCircular?.visibility= View.GONE
//    }
//
//    private fun checkUserFavorite() {
//        favoriteHelper = FavoriteHelper.getInstance(this)
//        if (favoriteHelper.checkId(usernameFav)) {
//            isFavorite = true
//            setStatusFavorite(isFavorite)
//        }
//
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
//                this.contentResolver.insert(CONTENT_URI, values)
//                Toast.makeText(this, "$usernameFav ${getString(R.string.add_favorite)}", Toast.LENGTH_SHORT).show()
//    }
//
//    private fun deletUserFavorite() {
//        favoriteHelper = FavoriteHelper.getInstance(this)
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
//
//}

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var image : String
    private lateinit var usernameFav : String
    private lateinit var userType : String

    private lateinit var uriwithid: Uri

    private var isFavorite = false
    private var favoriteData: FavoriteData? = null
    private lateinit var favoriteHelper: FavoriteHelper
    private lateinit var imgPhoto: String

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_FAV = "extra_data"
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_UPDATE = 200

        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_TYPE = "extra_type"
    }


    @SuppressLint("SetText18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = "Detail"

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(DetailViewModel::class.java)

        favoriteViewModel = ViewModelProvider (this, ViewModelProvider.AndroidViewModelFactory(application)).get(FavoriteViewModel::class.java)




//        detailViewModel.getUserDetail().observe(this, {
//            if (it != null) {
//                binding?.apply {
//                    detailName.text = it.name
//                    detailUsername.text = it.username
//                    detailLocation.text = it.userLocation
//                    detailRepository.text = it.publicRepos.toString()
//
//                    Glide.with(this@DetailActivity)
//                            .load(it.photoUrl)
//                            .into(detailPhoto)
//                    imgPhoto = it.photoUrl.toString()
//
//                    showLoading(false)
//                    binding?.btnFavorite?.setOnClickListener (this@DetailActivity)


//                    if (it != null) {
//                        favoriteData = FavoriteData(
//                                it.username,
//                                it.name,
//                                it.userLocation,
//                                it.photoUrl,
//                                it.publicRepos,
//                                it.followers.toString(),
//                                it.following.toString()
//                        )
//                    }
//                    isFavorite = true
//                }
//            }
//        })

//        val user = intent.getParcelableExtra<UserData>(EXTRA_DETAIL) as UserData
//        usernameFav = user.username!!
//
//        if (usernameFav != null) {
//            detailViewModel.setUserDetail(usernameFav)
//        }

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        favoriteData = intent.getParcelableExtra(EXTRA_NOTE)
        if (favoriteData != null)  {
            setDataObject()
            isFavorite = true
            val checked: Int = R.drawable.ic_baseline_favorite_24
            binding?.btnFavorite?.setImageResource(checked)
        } else {
            setData()
        }

        val sectionsPagerAdapter = DetailSectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tab)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        binding?.btnFavorite?.setOnClickListener (this)

        showLoading(true)

        checkUserFavorite()

//        uriwithid = Uri.parse(CONTENT_URI.toString() + "/" + favoriteData?.id)
//
//        val cursor = contentResolver.query(uriwithid, null, null, null, null)
//        if (cursor != null) {
//            favoriteData = MappingHelper.mapCursorToObject(cursor)
//            cursor.close()
//        }
    }

@SuppressLint("SetTextI18n", "StringFormatInvalid")
    private fun setData() {

    val user = intent.getParcelableExtra<UserData>(EXTRA_DETAIL) as UserData
    val username = user.username

    if (username  != null) {
        detailViewModel.setUserDetail(username )
    }

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
                imgPhoto = it.photoUrl.toString()

                showLoading(false)
            }
        }
    })
    }

    @SuppressLint("SetTextI18n")
    private fun setDataObject() {
        val favoriteUser = intent.getParcelableExtra<FavoriteData>(EXTRA_NOTE) as FavoriteData
        val username = favoriteUser.username

        if (username != null) {
            favoriteViewModel.setUserFavorite(username)
        }

        favoriteViewModel.getUserFavorite().observe(this, {
            if (it != null) {
                binding?.apply {
                    detailName.text = it.name
                    detailUsername.text = it.username.toString()
                    detailLocation.text = it.userLocation
                    detailRepository.text = it.publicRepos.toString()

                    Glide.with(this@DetailActivity)
                        .load(it.photoUrl)
                        .into(detailPhoto)
                    imgPhoto = it.photoUrl.toString()

                    showLoading(false)
                }
            }
        })
    }


    private fun showLoading(state: Boolean) {
        if (state)binding?.progressCircular?.visibility = View.VISIBLE
        else binding?.progressCircular?.visibility= View.GONE
    }

    override fun onClick(v: View?) {
        val checked: Int = R.drawable.ic_baseline_favorite_24
        val unChecked: Int = R.drawable.ic_baseline_favorite_border_24
        if (v?.id == R.id.btn_favorite) {
            if (isFavorite) {
//                usernameFav.let { detailViewModel.setUserDetail(it) }
//                if (favoriteHelper.checkId(favoriteData?.username.toString()))
                favoriteHelper.deleteById(favoriteData?.username.toString())
//                contentResolver.delete(uriwithid, null, null)
                Toast.makeText(this, getString(R.string.delet_favorite), Toast.LENGTH_SHORT).show()
                binding?.btnFavorite?.setImageResource(unChecked)
                isFavorite = false
                checkUserFavorite()

            } else {
                val dataUsername = binding?.detailUsername?.text.toString()
                val dataName = binding?.detailName?.text.toString()
                val dataPhoto = imgPhoto
                val dataLocation = binding?.detailLocation?.text.toString()
                val dataRepository = binding?.detailRepository?.text.toString()
                val dataFavorite = "1"

                val values = ContentValues()
                values.put(USERNAME, dataUsername)
                values.put(NAME, dataName)
                values.put(PHOTO_URL, dataPhoto)
                values.put(USER_LOCATION, dataLocation)
                values.put(PUBLIC_REPOS, dataRepository)
                values.put(FAVORITE, dataFavorite)

                isFavorite = true
                contentResolver.insert(CONTENT_URI, values)
                Toast.makeText(this, getString(R.string.add_favorite), Toast.LENGTH_SHORT).show()
                binding?.btnFavorite?.setImageResource(checked)
                checkUserFavorite()

            }
        }
    }


    private fun checkUserFavorite() {
        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        if (favoriteHelper.checkId(favoriteData?.username.toString())) {
            isFavorite = true
            val checked: Int = R.drawable.ic_baseline_favorite_24
            binding?.btnFavorite?.setImageResource(checked)
        }
    }

    private fun setStatusFavorite (statusFavorite: Boolean) {
        if (statusFavorite) binding?.btnFavorite?.setImageResource(R.drawable.ic_baseline_favorite_24)
        else binding?.btnFavorite?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteHelper.close()
    }
}


