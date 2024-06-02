package com.example.mygithubuser.appearance

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.userdata.network.response.GithubUser
import com.example.mygithubuser.databinding.ActivityFavoritesBinding
import com.example.mygithubuser.viewmodel.FavoritesUserViewModel
import com.example.mygithubuser.injection.ViewModelFactory

class FavoritesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorites"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(application))[FavoritesUserViewModel::class.java]

        val adapter = ListUsersAdapter()

        viewModel.favoritesUsers.observe(this) { users ->
            val items = arrayListOf<GithubUser>()
            users.map {
                val item = GithubUser(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            adapter.submitList(items)
            binding.progressBar.visibility = View.GONE
        }

        binding.rvUsersFavorites.adapter = adapter
        binding.rvUsersFavorites.layoutManager = LinearLayoutManager(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}