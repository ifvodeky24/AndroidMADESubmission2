package com.example.cataloguemovie.tvDetail

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cataloguemovie.R
import com.example.cataloguemovie.databinding.FragmentTvDetailBinding
import com.example.cataloguemovie.tv.TvViewModel
import com.example.core.domain.model.Tv
import com.example.core.utils.gone
import com.example.core.utils.visible
import com.example.core.vo.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class TvDetailFragment : Fragment() {
    private var tv: Tv? = null
    private var menu: Menu? = null

    companion object {
        const val TV = "TV"
    }

    private var _binding: FragmentTvDetailBinding? = null
    private val binding get() = _binding
    private val viewModel: TvViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTvDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let { arg ->
            tv = arg.getParcelable(TV)
        }

        tv?.id?.let { id ->
            viewModel.getDetailTv(id).observe(this, { movieDetail ->
                if (movieDetail != null) {
                    when (movieDetail) {
                        is Resource.Success -> {
                            binding?.progressBar?.gone()
                            binding?.content?.visible()
                            binding?.ivTvShow?.let {
                                Glide.with(requireActivity())
                                    .load("${"https://image.tmdb.org/t/p/w342"}${movieDetail.data?.posterPath}")
                                    .apply(
                                        RequestOptions.placeholderOf(R.drawable.ic_loading_image)
                                            .error(R.drawable.ic_error_image)
                                    )
                                    .into(it)
                            }

                            binding?.apply {
                                tvTitleTvShow.text = movieDetail.data?.name
                                tvFirstAirDateTvShow.text = resources.getString(
                                    R.string.firstAirDate,
                                    movieDetail.data?.firstAirDate
                                )
                                tvVoteAverageTvShow.text = resources.getString(
                                    R.string.vote,
                                    movieDetail.data?.voteAverage.toString()
                                )
                                tvOverviewTvShow.text = movieDetail.data?.overview
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

        tv?.id?.let { id ->
            viewModel.getDetailTv(id).observe(this, { movieDetail ->
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
            tv?.let { viewModel.setBookmark(it) }

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