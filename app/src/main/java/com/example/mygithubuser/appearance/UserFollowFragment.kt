package com.example.mygithubuser.appearance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.userdata.network.response.GithubUser
import com.example.mygithubuser.databinding.FragmentFollowBinding
import com.example.mygithubuser.viewmodel.UserFollowersViewModel
import com.example.mygithubuser.viewmodel.UserFollowingViewModel
import com.example.mygithubuser.viewmodel.MainViewModel

class UserFollowFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userFollowersViewModel: UserFollowersViewModel
    private lateinit var userFollowingViewModel: UserFollowingViewModel

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val position = it.getInt(ARG_POSITION)

            if (position == 1) {
                binding.rvFollowOfUser.layoutManager = LinearLayoutManager(requireActivity())
                userFollowersViewModel.listFollowers.observe(viewLifecycleOwner) { listFollowers ->
                    binding.progressBar.visibility = View.GONE
                    setFollow(listFollowers)
                }
            } else {
                binding.rvFollowOfUser.layoutManager = LinearLayoutManager(requireActivity())
                userFollowingViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
                    binding.progressBar.visibility = View.GONE
                    setFollow(listFollowing)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        userFollowersViewModel = ViewModelProvider(requireActivity())[UserFollowersViewModel::class.java]
        userFollowingViewModel = ViewModelProvider(requireActivity())[UserFollowingViewModel::class.java]
        return binding.root
    }

    private fun setFollow(consumerUsers: List<GithubUser>?) {
        val adapter = ListUsersAdapter()
        binding.rvFollowOfUser.adapter = adapter
        adapter.submitList(consumerUsers)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION: String = "0"
    }
}