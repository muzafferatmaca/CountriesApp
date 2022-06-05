package com.muzafferatmaca.countriesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.muzafferatmaca.countriesapp.R
import com.muzafferatmaca.countriesapp.model.Country
import com.muzafferatmaca.countriesapp.util.downloadFromUrl
import com.muzafferatmaca.countriesapp.util.placeholderProgressBar
import com.muzafferatmaca.countriesapp.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country_row.view.*
import kotlin.random.Random

/**
 * Created by Muzaffer Atmaca on 2.06.2022.
 */
class CountryAdapter(val countryList: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {


    class CountryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country_row, parent, false)

        return CountryViewHolder(view)

    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.view.countryNameTextView.text = countryList[position].countryName
        holder.view.regionTextView.text = countryList[position].countryRegion
        holder.view.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.view.countryFlagImageView.downloadFromUrl(countryList[position].imageUrl,placeholderProgressBar(holder.view.context))

    }

    override fun getItemCount(): Int {

        return countryList.size
    }

    fun updateCountryList(newCountryList: List<Country>) {

        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}