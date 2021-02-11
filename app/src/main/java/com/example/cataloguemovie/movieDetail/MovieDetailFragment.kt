package com.example.cataloguemovie.movieDetail

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cataloguemovie.BuildConfig
import com.example.cataloguemovie.R
import com.example.cataloguemovie.databinding.FragmentMovieDetailBinding
import com.example.cataloguemovie.movie.MovieViewModel
import com.example.core.domain.model.Movie
import com.example.core.utils.gone
import com.example.core.utils.visible
import com.example.core.vo.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment() {
    private var movie: Movie? = null
    private var menu: Menu? = null

    private val viewModel: MovieViewModel by viewModel()

    companion object {
        const val MOVIE = "MOVIE"
    }

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let { arg ->
            movie = arg.getParcelable(MOVIE)
        }

        movie?.id?.let { id ->
            viewModel.getDetailMovie(id).observe(this, { movieDetail ->
                if (movieDetail != null) {
                    when (movieDetail) {
                        is Resource.Success -> {
                            binding?.progressBar?.gone()
                            binding?.content?.visible()
                            binding?.ivPosterMovie?.let {
                                Glide.with(requireActivity())
                                    .load("${"https://image.tmdb.org/t/p/w342"}${movieDetail.data?.posterPath}")
                                    .apply(
                                        RequestOptions.placeholderOf(R.drawable.ic_loading_image)
                                            .error(R.drawable.ic_error_image)
                                    )
                                    .into(it)
                            }

                            binding?.apply {
                                tvTitleMovie.text = movieDetail.data?.title
                                tvReleaseDateMovie.text = resources.getString(
                                    R.string.releaseDate,
                                    movieDetail.data?.releaseDate
                                )
                                tvVoteAverageMovie.text = resources.getString(
                                    R.string.vote,
                                    movieDetail.data?.voteAverage.toString()
                                )
                                tvOverviewMovie.text = movieDetail.data?.overview
                            }

                        }

                        is Resource.Loading -> {
                            binding?.progressBar?.visible()
                        }

                        is Resource.Error -> {
                            binding?.progressBar?.gone()
                            binding?.errorConnection?.visible()
                        }
                    }
                }
            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        activity?.menuInflater?.inflate(R.menu.menu_detail, menu)
        this.menu = menu

        movie?.id?.let { id ->
            viewModel.getDetailMovie(id).observe(this, { movieDetail ->
                if (movieDetail != null) {
                    when (movieDetail) {
                        is Resource.Success -> {
                            binding?.progressBar?.gone()
                            binding?.content?.visible()

                            val state = movieDetail.data?.favorite
                            state?.let { setBookmarkState(it) }
                        }

                        is Resource.Loading -> {
                            binding?.progressBar?.visible()
                        }

                        is Resource.Error -> {
                            binding?.progressBar?.gone()
                            binding?.errorConnection?.visible()
                        }
                    }
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favourite) {
            movie?.let { viewModel.setBookmark(it) }

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBookmarkState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favourite)
        if (state) {
            menuItem?.icon =
                activity?.let { ContextCompat.getDrawable(it, R.drawable.ic_bookmarked_white) }
        } else {
            menuItem?.icon =
                activity?.let { ContextCompat.getDrawable(it, R.drawable.ic_bookmark_white) }
        }
    }
}