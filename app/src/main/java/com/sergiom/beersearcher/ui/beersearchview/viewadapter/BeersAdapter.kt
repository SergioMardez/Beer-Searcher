package com.sergiom.beersearcher.ui.beersearchview.viewadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergiom.beersearcher.databinding.BeerRecyclerLayoutBinding
import com.sergiom.data.models.BeerDataModel
import com.sergiom.data.models.BeerModel

class BeersAdapter(private val listener: ItemClickListener) : RecyclerView.Adapter<BeersViewHolder>() {
    private val items = ArrayList<BeerModel>()

    interface ItemClickListener {
        fun onItemClicked(item: BeerModel)
    }

    fun setItems(items: BeerDataModel) {
        this.items.clear()
        this.items.addAll(items.beers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {
        val binding: BeerRecyclerLayoutBinding = BeerRecyclerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int = items.size
}