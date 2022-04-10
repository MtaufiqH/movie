package com.taufiq.movies.presentation

import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.taufiq.movies.R
import com.taufiq.movies.databinding.ActivityMainBinding
import com.taufiq.movies.presentation.favorites.FavoritesFragment
import com.taufiq.movies.presentation.movies.MoviesFragment

class MainActivity : AppCompatActivity() {


    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        with(binding) {
            val pagerAdapter = PagerAdapter(this@MainActivity)
            vpMovies.adapter = pagerAdapter

            TabLayoutMediator(tbMoviesFavorite, vpMovies) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            supportActionBar?.elevation = 0f

        }

    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.label_movies_tab,
            R.string.label_favorite_tab
        )
    }

    inner class PagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            var fragment = when (position) {
                0 ->  MoviesFragment()
                1 ->  FavoritesFragment()
                else -> null
            }

            return fragment ?: Fragment()
        }

    }
}

