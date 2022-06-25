package com.sdk.planetnew.presentation.ui.home

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sdk.planetnew.data.model.Planet
import com.sdk.planetnew.databinding.HomeItemLayoutBinding

class HomeAdapter : ListAdapter<Planet, HomeAdapter.HomeViewHolder>(DiffCallBack()) {

    lateinit var onClick: (Planet) -> Unit

    private class DiffCallBack : DiffUtil.ItemCallback<Planet>() {
        override fun areItemsTheSame(oldItem: Planet, newItem: Planet): Boolean {
            return oldItem.num == newItem.num
        }

        override fun areContentsTheSame(oldItem: Planet, newItem: Planet): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            HomeItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HomeViewHolder(private val binding: HomeItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Planet) {
            binding.textView.text = data.title

            itemView.setOnClickListener {
                onClick.invoke(data)
            }

            Glide.with(binding.imageView)
                .load(data.image)
                .listener(listener(binding.progressbar))
                .into(binding.imageView)
        }
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
}