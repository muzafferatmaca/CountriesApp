package com.muzafferatmaca.countriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.muzafferatmaca.countriesapp.R
import com.muzafferatmaca.countriesapp.adapter.CountryAdapter
import com.muzafferatmaca.countriesapp.viewmodel.FeedVM
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    private lateinit var viewModel : FeedVM
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(FeedVM::class.java)
        viewModel.refreshData()

        countryRecyclerViewList.layoutManager = LinearLayoutManager(context)
        countryRecyclerViewList.adapter = countryAdapter

        swipeRefreshLayout.setOnRefreshListener {

            countryRecyclerViewList.visibility = View.GONE
            countryError.visibility = View.GONE
            countryProgressBar.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            swipeRefreshLayout.isRefreshing = false

        }

        observerLiveData()

    }

  private fun observerLiveData(){

        viewModel.countries.observe(viewLifecycleOwner){countries ->

            countries?.let {

                countryRecyclerViewList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)


            }
        }

        viewModel.countryError.observe(viewLifecycleOwner){error ->

            error?.let {

                if (it){
                    countryError.visibility = View.VISIBLE
                }else{
                    countryError.visibility = View.GONE
                }

            }
        }

        viewModel.countryLoading.observe(viewLifecycleOwner){progress ->

            progress?.let {

                if (it){
                    countryProgressBar.visibility = View.VISIBLE
                    countryRecyclerViewList.visibility = View.GONE
                    countryError.visibility = View.GONE
                }else{
                    countryProgressBar.visibility = View.GONE
                }

            }

        }
    }
}