package com.sdk.planetnew.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.sdk.planetnew.R
import com.sdk.planetnew.databinding.FragmentHomeBinding
import com.sdk.planetnew.presentation.activity.MainActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeAdapter: HomeAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        homeAdapter = HomeAdapter()
        setupRv()
        observe()
        homeAdapter.onClick = {
            val bundle = bundleOf("planet" to it)
            view.findNavController().navigate(R.id.action_nav_main_to_detailFragment, bundle)
        }
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun observe() {
        homeViewModel.getPlanetList().observe(viewLifecycleOwner) { list ->
            homeAdapter.submitList(list)
            binding.progressbar.isVisible = false
        }
    }

    private fun setupRv() = binding.recyclerView.apply {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = homeAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}