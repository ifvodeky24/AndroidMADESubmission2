package com.example.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.core.ui.ViewPagerAdapter
import com.example.favourite.databinding.FragmentFavouriteBinding
import com.example.favourite.di.favoriteModule
import org.koin.core.context.loadKoinModules

class FavouriteFragment : Fragment() {

    private lateinit var adapter: ViewPagerAdapter

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadKoinModules(favoriteModule)

        childFragmentManager.let {
            adapter = ViewPagerAdapter(it)
        }

        adapter.addFragment(MovieFavouriteFragment(), "Movie Fav")
        adapter.addFragment(TvFavouriteFragment(), "Tv Fav")
        adapter.notifyDataSetChanged()

        binding?.viewPager?.adapter = adapter
        binding?.tabs?.setupWithViewPager(binding?.viewPager)
    }
}