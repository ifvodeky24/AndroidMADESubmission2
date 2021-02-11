package com.example.cataloguemovie.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cataloguemovie.R
import com.example.cataloguemovie.databinding.FragmentTvBinding
import com.example.cataloguemovie.tvDetail.TvDetailFragment
import com.example.core.domain.model.Tv
import com.example.core.ui.TvAdapter
import com.example.core.ui.TvInterface
import com.example.core.utils.gone
import com.example.core.utils.visible
import com.example.core.vo.Resource
import org.koin.android.viewmodel.ext.android.viewModel

class TvFragment : Fragment(), TvInterface {
    private var tvList: ArrayList<Tv> = arrayListOf()
    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding

    private val viewModel: TvViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getAllTv().observe(this, { tv ->
            if (tv != null) {
                when (tv) {
                    is Resource.Success -> {
                        binding?.progressBar?.gone()
                        binding?.rvTv?.setHasFixedSize(true)
                        binding?.rvTv?.layoutManager = LinearLayoutManager(activity)
                        tv.data?.let {
                            tvList.addAll(it)
                            val tvAdapter = TvAdapter(it, this)
                            binding?.rvTv?.adapter = tvAdapter
                            tvAdapter.notifyDataSetChanged()
                        }
                    }

                    is Resource.Loading -> {
                        binding?.progressBar?.visible()
                    }

                    is Resource.Error -> {
                        binding?.errorConnection?.visible()
                        binding?.rvTv?.gone()
                        binding?.progressBar?.gone()
                    }

                }
            }
        })
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

}