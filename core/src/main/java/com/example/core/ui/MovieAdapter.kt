package com.example.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.BuildConfig
import com.example.core.R
import com.example.core.databinding.ItemMovieBinding
import com.example.core.domain.model.Movie

class MovieAdapter(
    private val movieList: List<Movie>,
    val movieInterface: MovieInterface
) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieBinding.bind(itemView)
        fun bind(movie: Movie) {
            with(binding) {
                tvTitleMovie.text = movie.title
                tvOverviewMovie.text = movie.overview
                tvReleaseDateMovie.text = movie.releaseDate
                tvVoteAverageMovie.text = movie.voteAverage.toString()

                Glide.with(binding.root)
                    .load("${"https://image.tmdb.org/t/p/w342"}${movie.posterPath}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading_image)
                            .error(R.drawable.ic_error_image)
                    )
                    .into(ivPosterMovie)

                root.setOnClickListener {
                    movieInterface.click(movie)
                }

                ivShare.setOnClickListener {
                    movieInterface.shareClick(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(movieList[position])

    override fun getItemCount(): Int = movieList.size
}
