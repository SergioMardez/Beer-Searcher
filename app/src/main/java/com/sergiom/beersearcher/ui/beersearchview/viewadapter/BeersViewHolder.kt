package com.sergiom.beersearcher.ui.beersearchview.viewadapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sergiom.beersearcher.R
import com.sergiom.beersearcher.databinding.BeerRecyclerLayoutBinding
import com.sergiom.data.models.BeerModel

class BeersViewHolder(private val itemBinding: BeerRecyclerLayoutBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: BeerModel, listener: BeersAdapter.ItemClickListener) {
        itemBinding.root.setOnClickListener {
            listener.onItemClicked(item)
        }
        itemBinding.beerName.text = item.name
        Glide.with(itemBinding.root.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .fallback(R.drawable.ic_placeholder)
            .into(itemBinding.beerImage)
    }
}