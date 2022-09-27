package com.sergiom.beersearcher.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.sergiom.beersearcher.R
import com.sergiom.beersearcher.databinding.DetailViewBinding
import com.sergiom.data.models.BeerItemDataBase

class DetailView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {

    var binding: DetailViewBinding = DetailViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setDetail(beer: BeerItemDataBase?) {
        beer?.let { beer ->
            binding.apply {
                beerName.text = beer.name
                beerTagline.text = beer.tagline
                beerAlcohol.text = root.context.getString(R.string.beer_alcohol_text, beer.abv.toString())
                beerBrewed.text = root.context.getString(R.string.beer_brewed_text, beer.firstBrewed)
                beerDescription.text = beer.description
                beerTips.text = root.context.getString(R.string.beer_tips_text, beer.brewersTips)
                beerFoodPairing.text = root.context.getString(R.string.beer_food_pairing_text, getFoodPairingString(beer.foodPairing))
                Glide.with(root.context)
                    .load(beer.imageUrl)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .fallback(R.drawable.ic_placeholder)
                    .into(beerImage)
            }
        }
    }

    private fun getFoodPairingString(foods: List<String>): String {
        var resultString = ""
        foods.forEach {
            resultString = "$it, "
        }
        return resultString.dropLast(2)
    }

}