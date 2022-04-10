package com.taufiq.movies.presentation.favorites

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.taufiq.movies.BuildConfig
import com.taufiq.movies.R
import com.taufiq.movies.databinding.MoviesItemBinding
import com.taufiq.movies.domain.model.Movie
import com.taufiq.movies.utils.BaseAdapter

class FavoriteMovieAdapter(private val context: Context,private val listener: (Movie) -> Unit) :
    BaseAdapter<Movie>(R.layout.movies_item) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val movies = data[position]
        val binding = MoviesItemBinding.bind(holder.itemView)
        with(binding) {
            Glide.with(context)
                .load(BuildConfig.BASE_IMAGE_URL + movies.posterPath)
                .placeholder(
                    ColorDrawable(
                        ContextCompat.getColor(
                            context,
                            R.color.cardview_dark_background
                        )
                    )
                ).into(ivPoster)

            tvTitle.text = movies.originalTitle
            tvOverview.text = movies.overview

            if (movies.isFavorite) {
                ivActionFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                ivActionFavorite.setImageResource(R.drawable.ic_fav_border)
            }

            ivActionFavorite.setOnClickListener {
                listener.invoke(movies)
                movies.isFavorite = !movies.isFavorite
            }
        }
    }
}