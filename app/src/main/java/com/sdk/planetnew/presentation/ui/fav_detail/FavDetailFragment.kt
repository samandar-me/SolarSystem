package com.sdk.planetnew.presentation.ui.fav_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.sdk.planetnew.R
import com.sdk.planetnew.data.model.Number
import com.sdk.planetnew.data.model.Planet
import com.sdk.planetnew.databinding.FragmentFavDetailBinding
import com.sdk.planetnew.domain.database.PlanetDatabase
import com.sdk.planetnew.domain.favorite.FavDetailRepository
import com.sdk.planetnew.domain.manager.MyClipBoardManager
import com.sdk.planetnew.data.util.extensions.snack

class FavDetailFragment : Fragment() {

    private var _binding: FragmentFavDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var planet: Planet
    private lateinit var viewModel: FavDetailViewModel

    private lateinit var myClipBoardManager: MyClipBoardManager
    private val rotateOpen by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_bottom_anim
        )
    }
    private val toBottom by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.to_bottom_anim
        )
    }

    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    private fun initViews(view: View) {
        planet = arguments?.getParcelable("planet")!!
        val repository = FavDetailRepository(
            PlanetDatabase.invoke(requireContext())
        )
        val viewModelFactory = FavDetailViewModelFactory(
            activity?.application!!,
            repository
        )
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[FavDetailViewModel::class.java]
        myClipBoardManager = MyClipBoardManager(requireContext())

        binding.baseFab.setOnClickListener {
            baseFabClicked()
        }
        Glide.with(binding.imageView)
            .load(planet.backImage)
            .into(binding.imageView)
        binding.textTitle.text = planet.title
        binding.textDesc.text = planet.desc
        binding.delFav.setOnClickListener {
            deleteFromDatabase(planet)
            view.findNavController().popBackStack()
        }
        nestedScroll()
        binding.copyFab.setOnClickListener {
            copyText()
        }
    }

    private fun baseFabClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.delFav.startAnimation(fromBottom)
            binding.copyFab.startAnimation(fromBottom)
            binding.baseFab.startAnimation(rotateOpen)
        } else {
            binding.delFav.startAnimation(toBottom)
            binding.copyFab.startAnimation(toBottom)
            binding.baseFab.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.delFav.isVisible = true
            binding.copyFab.isVisible = true
        } else {
            binding.delFav.visibility = View.INVISIBLE
            binding.copyFab.visibility = View.INVISIBLE
        }
    }

    private fun deleteFromDatabase(planet: Planet) {
        viewModel.deleteFromDatabase(planet)
        viewModel.deleteNumber(Number(planet.id, planet.num))
    }

    private fun nestedScroll() {
        binding.nestedScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                binding.baseFab.hide()
            } else {
                binding.baseFab.show()
            }
        })

        binding.nestedScroll.viewTreeObserver.addOnScrollChangedListener {
            if (clicked) {
                baseFabClicked()
            }
        }
    }

    private fun copyText() {
        val text =
            binding.textTitle.text.toString().trim() + "\n" + binding.textDesc.text.toString()
                .trim()
        myClipBoardManager.copyText(text)
        snack(binding.root, getString(R.string.nus))
    }
}