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
import com.example.cataloguemovie.tvDetail.TvDetailFragment
import com.example.core.domain.model.Tv
import com.example.core.ui.TvFavouriteAdapter
import com.example.core.ui.TvInterface
import com.example.core.utils.gone
import com.example.core.utils.visible
import com.example.favourite.databinding.FragmentTvFavouriteBinding
import org.koin.android.viewmodel.ext.android.viewModel

class TvFavouriteFragment : Fragment(), TvInterface {
    private var tvList: ArrayList<Tv> = arrayListOf()
    private var _binding: FragmentTvFavouriteBinding? = null
    private val binding get() = _binding

    private lateinit var tvAdapter: TvFavouriteAdapter
    private val viewModel: FavouriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTvFavouriteBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvAdapter = TvFavouriteAdapter(this)
        binding?.progressBar?.visible()
        viewModel.getTvFavourites().observe(this, { tv ->
            binding?.progressBar?.gone()

            if (tv.isNotEmpty()) {
                binding?.rvTv?.visible()
                binding?.noData?.gone()
                tvAdapter.setMovies(tv)
                tvAdapter.notifyDataSetChanged()
            } else {
                binding?.rvTv?.gone()
                binding?.noData?.visible()
            }

        })

        binding?.rvTv?.layoutManager = LinearLayoutManager(context)
        binding?.rvTv?.setHasFixedSize(true)
        binding?.rvTv?.adapter = tvAdapter
    }

    override fun click(tv: Tv) {
        val bundle = Bundle()
        bundle.putParcelable(TvDetailFragment.TV, tv)
        findNavController().navigate(R.id.tvDetailFragment, bundle)
    }

    override fun shareClick(tv: Tv) {
        activity?.let {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(it)
                .setType(mimeType)
                .setChooserTitle(getString(R.string.send_application))
                .setText(resources.getString(R.string.share_text, tv.name))
                .startChooser()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}