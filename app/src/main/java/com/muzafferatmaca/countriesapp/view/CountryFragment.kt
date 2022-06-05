package com.muzafferatmaca.countriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.muzafferatmaca.countriesapp.R
import com.muzafferatmaca.countriesapp.util.downloadFromUrl
import com.muzafferatmaca.countriesapp.util.placeholderProgressBar
import com.muzafferatmaca.countriesapp.viewmodel.CountryVM
import kotlinx.android.synthetic.main.fragment_country.*


class CountryFragment : Fragment() {


    private lateinit var viewModel : CountryVM
    private var countryUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid

        }
        viewModel = ViewModelProvider(this).get(CountryVM::class.java)
        viewModel.getDataFromRoom(countryUuid)



        observeLiveData()

    }

   private fun observeLiveData(){

        viewModel.countryLiveData.observe(viewLifecycleOwner){country ->

            country?.let {

                countryName.text = country.countryName
                capitalName.text = country.countryCapital
                regionName.text = country.countryRegion
                currencyName.text = country.countryCurrency
                languageName.text = country.countryLanguage
                context?.let {

                    countryImageView.downloadFromUrl(country.imageUrl, placeholderProgressBar(it))

                }

            }

        }


    }

}