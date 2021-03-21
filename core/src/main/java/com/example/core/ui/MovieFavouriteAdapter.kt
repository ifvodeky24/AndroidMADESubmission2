package com.example.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.R
import com.example.core.databinding.ItemMovieBinding
import com.example.core.domain.model.Movie
import java.util.*

class MovieFavouriteAdapter(val movieInterface: MovieInterface) :
    RecyclerView.Adapter<MovieFavouriteAdapter.ViewHolder>() {

    private val movieList = ArrayList<Movie>()

    fun setMovies(movies: List<Movie>?) {
        if (movies == null) return
        this.movieList.clear()
        this.movieList.addAll(movies)

        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemsMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = movieList[position]
        holder.bind(course)
    }

    override fun getItemCount(): Int = movieList.size

    inner class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
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
}