package com.example.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.core.BuildConfig
import com.example.core.R
import com.example.core.databinding.ItemTvBinding
import com.example.core.domain.model.Tv

class TvAdapter(private val tvList: List<Tv>, val tvInterface: TvInterface) :
    RecyclerView.Adapter<TvAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTvBinding.bind(itemView)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(tvList[position])

    override fun getItemCount(): Int = tvList.size
}