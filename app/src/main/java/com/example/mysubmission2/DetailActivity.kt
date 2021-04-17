package com.example.mysubmission2

import android.content.ContentValues
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
import com.example.mysubmission2.database.DatabaseFavorite
import com.example.mysubmission2.database.DatabaseFavorite.FavColumns.Companion.CONTENT_URI
import com.example.mysubmission2.database.FavoriteHelper
import com.example.mysubmission2.databinding.ActivityDetailBinding
import com.example.mysubmission2.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding
    private lateinit var detailViewModel: DetailViewModel

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
        const val EXTRA_NOTE = "extra_note"
        const val EXTRA_POSITION = "extra_position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = "Detail"

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        val user = intent.getParcelableExtra<UserData>(EXTRA_DETAIL) as UserData
        val username = user.username

        favoriteData = intent.getParcelableExtra(EXTRA_NOTE)
        if (favoriteData != null) {
            setDataFavorite()
            isFavorite = true
            val checked: Int = R.drawable.ic_baseline_favorite_24
            binding?.btnFavorite?.setImageResource(checked)
        } else {
            favoriteData = FavoriteData()
        }

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
        binding?.btnFavorite?.setOnClickListener(this)
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            this.title = title
        }
    }

    private fun setDataFavorite() {
        val userFavorite = intent.getParcelableExtra(EXTRA_NOTE) as FavoriteData?
        userFavorite?.name.let {
            if (it != null) {
                setActionBarTitle(it)
            }
            binding?.detailName?.text = favoriteData?.name
            binding?.detailUsername?.text = favoriteData?.username
            binding?.detailLocation?.text = favoriteData?.userLocation
            binding?.detailRepository?.text = favoriteData?.publicRepos.toString()

            binding?.let { it1 ->
                Glide.with(this)
                        .load(favoriteData?.photoUrl)
                        .into(it1.detailPhoto)
            }
            imgPhoto = favoriteData?.photoUrl.toString()

        }
    }

    private fun showLoading(state: Boolean) {
        if (state)binding?.progressCircular?.visibility = View.VISIBLE
        else binding?.progressCircular?.visibility= View.GONE
    }

    override fun onClick(v: View?) {
        val clicked: Int = R.drawable.ic_baseline_favorite_24
        val unClicked: Int = R.drawable.ic_baseline_favorite_border_24
        if (v?.id == R.id.btn_favorite) {
            if (isFavorite) {
//                val values = ContentValues().apply {
//                    put(DatabaseFavorite.FavColumns.USERNAME, detailViewModel.getUserDetail().value?.username)
//                    put(DatabaseFavorite.FavColumns.NAME, detailViewModel.getUserDetail().value?.name)
//                    put(DatabaseFavorite.FavColumns.USER_LOCATION, detailViewModel.getUserDetail().value?.userLocation)
//                    put(DatabaseFavorite.FavColumns.PUBLIC_REPOS, detailViewModel.getUserDetail().value?.publicRepos)
//                    put(DatabaseFavorite.FavColumns.PHOTO_URL, detailViewModel.getUserDetail().value?.photoUrl)
//                }
//                activity?.contentResolver.insert(CONTENT_URI, values)
//                Toast.makeText(this, getString(R.string.add_favorite), Toast.LENGTH_SHORT).show()
                favoriteHelper.deleteById(favoriteData?.username.toString())
                Toast.makeText(this, getString(R.string.delet_favorite), Toast.LENGTH_SHORT).show()
                binding?.btnFavorite?.setImageResource(unClicked)
                isFavorite = false
//                isFavorite = !isFavorite
//                setFavoriteState (isFavorite)
//                checkUserFavorite()
            } else {
//                favoriteHelper = FavoriteHelper.getInstance(requireContext())
//                favoriteHelper.open()
//                if (favoriteHelper.che)

                val dataUsername = binding?.detailUsername?.text.toString()
                val dataName = binding?.detailName?.text.toString()
                val dataLocation = binding?.detailLocation?.text.toString()
                val dataRepository = binding?.detailRepository?.text.toString()
                val dataPhoto = binding?.detailPhoto.toString()
                val dataFavorite = "1"

                val values = ContentValues()
                values.put(DatabaseFavorite.FavColumns.USERNAME, dataUsername)
                values.put(DatabaseFavorite.FavColumns.NAME, dataName)
                values.put(DatabaseFavorite.FavColumns.USER_LOCATION, dataLocation)
                values.put(DatabaseFavorite.FavColumns.PUBLIC_REPOS, dataRepository)
                values.put(DatabaseFavorite.FavColumns.PHOTO_URL, dataPhoto)
                values.put(DatabaseFavorite.FavColumns.FAVORITE, dataFavorite)

                isFavorite = true
                contentResolver.insert(CONTENT_URI, values)
                Toast.makeText(this, getString(R.string.add_favorite), Toast.LENGTH_SHORT).show()
                binding?.btnFavorite?.setImageResource(clicked)

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteHelper.close()
    }
}

