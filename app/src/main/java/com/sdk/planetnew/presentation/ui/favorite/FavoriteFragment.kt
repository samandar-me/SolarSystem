package com.sdk.planetnew.presentation.ui.favorite

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdk.planetnew.R
import com.sdk.planetnew.data.model.Planet
import com.sdk.planetnew.data.util.extensions.snack
import com.sdk.planetnew.databinding.FragmentFavoriteBinding
import com.sdk.planetnew.domain.database.PlanetDatabase
import com.sdk.planetnew.domain.favorite.FavoriteRepository
import com.sdk.planetnew.presentation.ui.home.HomeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        homeAdapter = HomeAdapter()
        val repository = FavoriteRepository(
            PlanetDatabase(requireContext())
        )
        val favoriteViewModelFactory = FavoriteViewModelFactory(
            activity?.application!!,
            repository
        )
        viewModel = ViewModelProvider(
            requireActivity(),
            favoriteViewModelFactory
        )[FavoriteViewModel::class.java]
        setupRv()
        viewModel.getAllPlanets().observe(viewLifecycleOwner) { list ->
            binding.linearLayout.isVisible = list.isEmpty()
            binding.fabDelete.isVisible = list.isNotEmpty()
            homeAdapter.submitList(list)
        }
        binding.fabDelete.setOnClickListener {
            showDialog()
        }
        homeAdapter.onClick = {
            val bundle = bundleOf("planet" to it)
            view.findNavController().navigate(R.id.action_nav_favorite_to_favDetailFragment, bundle)
        }
        hideShowFloating()
    }

    private fun setupRv() = binding.recyclerView.apply {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = homeAdapter
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setPositiveButton(R.string.ha) { _, _ ->
                viewModel.deleteAllPlanets()
                snack(binding.root, getString(R.string.tan))
                viewModel.deleteAllNumbers()
            }
            setNegativeButton(R.string.yoq, null)
            setTitle(R.string.dialog)
            setMessage(R.string.och)
        }.show()
    }

    private fun hideShowFloating() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    if (binding.fabDelete.isShown) {
                        binding.fabDelete.hide()
                    }
                } else if (dy < 0) {
                    if (!binding.fabDelete.isShown) {
                        binding.fabDelete.show()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}