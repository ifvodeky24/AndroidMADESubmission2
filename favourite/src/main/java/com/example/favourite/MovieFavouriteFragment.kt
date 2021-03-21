package com.example.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cataloguemovie.R
import com.example.cataloguemovie.movieDetail.MovieDetailFragment
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.domain.model.Movie
import com.example.core.ui.MovieFavouriteAdapter
import com.example.core.ui.MovieInterface
import com.example.core.utils.gone
import com.example.core.utils.visible
import com.example.favourite.databinding.FragmentMovieFavouriteBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFavouriteFragment : Fragment(), MovieInterface {

    private var movieList: ArrayList<MovieEntity> = arrayListOf()
    private var _binding: FragmentMovieFavouriteBinding? = null
    private val binding get() = _binding

    private lateinit var movieAdapter: MovieFavouriteAdapter
    private val viewModel: FavouriteViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieFavouriteBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieAdapter = MovieFavouriteAdapter(this)
        binding?.progressBar?.visible()
        viewModel.getMovieFavourites().observe(this, { movie ->
            binding?.progressBar?.gone()

            if (movie.isNotEmpty()) {
                binding?.rvMovie?.visible()
                binding?.noData?.gone()
                movieAdapter.setMovies(movie)
                movieAdapter.notifyDataSetChanged()
            } else {
                binding?.rvMovie?.gone()
                binding?.noData?.visible()
            }
        })

        binding?.rvMovie?.layoutManager = LinearLayoutManager(context)
        binding?.rvMovie?.setHasFixedSize(true)
        binding?.rvMovie?.adapter = movieAdapter
    }

    override fun click(movie: Movie) {
        val bundle = Bundle()
        bundle.putParcelable(MovieDetailFragment.MOVIE, movie)
        findNavController().navigate(R.id.movieDetailFragment, bundle)
    }

    override fun shareClick(movie: Movie) {
        activity?.let {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(it)
                .setType(mimeType)
                .setChooserTitle(getString(R.string.send_application))
                .setText(resources.getString(R.string.share_text, movie.title))
                .startChooser()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}