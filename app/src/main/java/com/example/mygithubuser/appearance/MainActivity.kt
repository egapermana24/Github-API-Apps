package com.example.mygithubuser.appearance

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.R
import com.example.mygithubuser.userdata.network.response.GithubUser
import com.example.mygithubuser.databinding.ActivityMainBinding
import com.example.mygithubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel by viewModels<MainViewModel>()
    private var querySearch = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _textView, _actionId, _event ->
                    querySearch = searchView.text.toString()
                    mainActivityViewModel.searchForUsers(querySearch)
                    searchView.hide()
                    searchBar.text = querySearch
                    false
                }
        }


        val layoutManager = LinearLayoutManager(this)
        binding.rvListUsers.layoutManager = layoutManager

        mainActivityViewModel.listUsers.observe(this) { listUsers ->
            setUsersData(listUsers)
        }
        mainActivityViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setUsersData(consumerUsers: List<GithubUser>) {
        val adapter = ListUsersAdapter()
        adapter.submitList(consumerUsers)
        binding.rvListUsers.adapter = adapter
        binding.searchBar.text = ""
        if (querySearch != "ega") {
            binding.searchBar.text = querySearch
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_page -> {
                val intent = Intent(this, FavoritesActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.about_page -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.home_page -> {
                querySearch = "ega"
                mainActivityViewModel.searchForUsers(querySearch)
                binding.searchBar.text = ""
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}