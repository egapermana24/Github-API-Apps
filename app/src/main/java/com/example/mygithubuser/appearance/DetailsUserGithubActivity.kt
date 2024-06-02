package com.example.mygithubuser.appearance

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithubuser.R
import com.example.mygithubuser.userdata.localdata.`object`.FavoritesUser
import com.example.mygithubuser.databinding.ActivityDetailsUserGithubBinding
import com.example.mygithubuser.injection.ViewModelFactory
import com.example.mygithubuser.viewmodel.DetailsUserGithubViewModel
import com.example.mygithubuser.viewmodel.UserFollowersViewModel
import com.example.mygithubuser.viewmodel.UserFollowingViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailsUserGithubActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsUserGithubBinding
    private val userFollowersViewModel by viewModels<UserFollowersViewModel>()
    private val userFollowingViewModel by viewModels<UserFollowingViewModel>()
    private val detailsUserGithubViewModel by viewModels<DetailsUserGithubViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityDetailsUserGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val avatarUrl = intent.getStringExtra(AVATAR_KEY)
        val username = intent.getStringExtra(USERNAME_KEY)

        if (username != null) {
            detailsUserGithubViewModel.getDetailsUser(username)

            detailsUserGithubViewModel.getFavoriteUserByUsername(username).observe(this) { favoriteUser ->
                val user = FavoritesUser(username, avatarUrl)

                if (favoriteUser == null) {
                    binding.fabFavorite.setOnClickListener {
                        detailsUserGithubViewModel.saveFavorites(user)
                        binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                    }
                } else {
                    binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                    binding.fabFavorite.setOnClickListener {
                        detailsUserGithubViewModel.deleteFavorites(favoriteUser)
                        binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                    }
                }
                binding.fabShare.setOnClickListener {
                    detailsUserGithubViewModel.shareGithubProfile(this@DetailsUserGithubActivity, username)
                }
            }
        }

        detailsUserGithubViewModel.detailsUser.observe(this) { detailUser ->
            binding.tvName.text = detailUser.name
            binding.tvGithubUsername.text = "@${detailUser.login}"
            binding.tvUrlRepo.text = detailUser.htmlUrl
            Glide.with(this).load(detailUser.avatarUrl).into(binding.imgUserAvatar)
            binding.countFollowers.text = detailUser.followers.toString()
            binding.countFollowings.text = detailUser.following.toString()
        }

        detailsUserGithubViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        userFollowersViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        userFollowingViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        if (username != null) {
            sectionsPagerAdapter.username = username

            val viewPager: ViewPager2 = binding.viewPager
            viewPager.adapter = sectionsPagerAdapter

            val tabs: TabLayout = binding.tabs
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> {
                        userFollowersViewModel.getFollowers(username)
                        "Followers"
                    }

                    1 -> {
                        userFollowingViewModel.getFollowing(username)
                        "Following"
                    }

                    else -> ""
                }
            }.attach()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val USERNAME_KEY = "username_data"
        const val AVATAR_KEY = "avatar_data"
    }
}