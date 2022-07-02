package com.sdk.planetnew.presentation.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sdk.planetnew.R
import com.sdk.planetnew.data.model.Number
import com.sdk.planetnew.data.model.Planet
import com.sdk.planetnew.data.util.extensions.admob
import com.sdk.planetnew.databinding.FragmentDetailBinding
import com.sdk.planetnew.domain.database.PlanetDatabase
import com.sdk.planetnew.domain.detail.DetailRepository
import com.sdk.planetnew.domain.manager.MyClipBoardManager
import com.sdk.planetnew.data.util.extensions.snack
import com.sdk.planetnew.data.util.extensions.toast
import com.sdk.planetnew.domain.manager.MySharedPref

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var mySharedPref: MySharedPref

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
    private lateinit var viewModel: DetailViewModel
    private lateinit var planetDatabase: PlanetDatabase

    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val planet = arguments?.getParcelable<Planet>("planet")!!
        myClipBoardManager = MyClipBoardManager(requireContext())
        mySharedPref = MySharedPref(requireContext())
        planetDatabase = PlanetDatabase(requireContext())
        val detailRepository = DetailRepository(
            planetDatabase
        )
        val viewModelFactory = DetailViewModelFactory(
            activity?.application!!,
            detailRepository
        )
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[DetailViewModel::class.java]
        binding.baseFab.setOnClickListener {
            baseFabClicked()
        }
        nestedScroll()
        binding.textTitle.text = planet.title
        binding.textDesc.text = planet.desc
        activity?.title = planet.title

        Glide.with(binding.imageView)
            .load(planet.backImage)
            .listener(listener(binding.progressBar))
            .into(binding.imageView)

        binding.copyFab.setOnClickListener {
            copyText()
        }
        binding.favFab.setOnClickListener {
            saveToDatabase(planet)
        }
        admob(binding.admob)
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

    private fun baseFabClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.favFab.startAnimation(fromBottom)
            binding.copyFab.startAnimation(fromBottom)
            binding.baseFab.startAnimation(rotateOpen)
        } else {
            binding.favFab.startAnimation(toBottom)
            binding.copyFab.startAnimation(toBottom)
            binding.baseFab.startAnimation(rotateClose)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.favFab.isVisible = true
            binding.copyFab.isVisible = true
        } else {
            binding.favFab.visibility = View.INVISIBLE
            binding.copyFab.visibility = View.INVISIBLE
        }
    }

    private fun saveToDatabase(planet: Planet) {
        if (!viewModel.getAllNumbers().contains(Number(planet.id, planet.num))) {
            viewModel.savePlanet(planet)
            viewModel.saveNumber(Number(planet.id, planet.num))
            snack(binding.root, getString(R.string.sv))
        } else {
            snack(binding.root, getString(R.string.all))
        }
    }

    private fun copyText() {
        val text =
            binding.textTitle.text.toString().trim() + "\n" + binding.textDesc.text.toString()
                .trim()
        myClipBoardManager.copyText(text)
        snack(binding.root, getString(R.string.nus))
    }

    private fun listener(progressBar: ProgressBar) = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            progressBar.isVisible = false
            return false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}