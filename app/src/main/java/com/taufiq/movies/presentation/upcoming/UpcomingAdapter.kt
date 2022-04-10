package com.taufiq.movies.presentation.upcoming

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.taufiq.movies.BuildConfig
import com.taufiq.movies.R
import com.taufiq.movies.databinding.UpcomingItemBinding
import com.taufiq.movies.domain.model.Upcoming
import com.taufiq.movies.utils.BaseAdapter

class UpcomingAdapter(private val context: Context) : BaseAdapter<Upcoming>(R.layout.upcoming_item) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val upcoming = data[position]
        limit = 5
        val binding = UpcomingItemBinding.bind(holder.itemView)
        Glide.with(context)
            .load(BuildConfig.BASE_IMAGE_URL + upcoming.posterPath)
            .placeholder(
                ColorDrawable(
                    ContextCompat.getColor(
                        context,
                        R.color.cardview_dark_background
                    )
                )
            ).into(binding.ivPosterUpcoming)


    }
}