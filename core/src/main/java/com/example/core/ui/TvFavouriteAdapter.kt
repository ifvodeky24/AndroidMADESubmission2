package com.example.core.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.BuildConfig
import com.example.core.R
import com.example.core.databinding.ItemTvBinding
import com.example.core.domain.model.Tv
import java.util.*

class TvFavouriteAdapter(val tvInterface: TvInterface) :
    RecyclerView.Adapter<TvFavouriteAdapter.ViewHolder>() {

    private val tvList = ArrayList<Tv>()

    fun setMovies(tvs: List<Tv>?) {
        if (tvs == null) return
        this.tvList.clear()
        this.tvList.addAll(tvs)

        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemsTvBinding =
            ItemTvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemsTvBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = tvList[position]
        holder.bind(course)
    }

    override fun getItemCount(): Int = tvList.size

    inner class ViewHolder(private val binding: ItemTvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: Tv) {
            with(binding) {
                tvTitleTvShow.text = tv.name
                tvOverviewTvShow.text = tv.overview
                tvFirstAirDateTvShow.text = tv.firstAirDate
                tvVoteAverageTvShow.text = tv.voteAverage.toString()
                
                Glide.with(binding.root)
                    .load("${"https://image.tmdb.org/t/p/w342"}${tv.posterPath}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading_image)
                            .error(R.drawable.ic_error_image)
                    )
                    .into(ivTvShow)

                root.setOnClickListener {
                    tvInterface.click(tv)
                }

                ivShare.setOnClickListener {
                    tvInterface.shareClick(tv)
                }
            }
        }
    }
}