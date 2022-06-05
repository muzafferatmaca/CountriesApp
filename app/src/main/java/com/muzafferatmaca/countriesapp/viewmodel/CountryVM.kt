package com.muzafferatmaca.countriesapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muzafferatmaca.countriesapp.model.Country
import com.muzafferatmaca.countriesapp.service.CountryDatabase
import kotlinx.coroutines.launch

/**
 * Created by Muzaffer Atmaca on 2.06.2022.
 */
class CountryVM(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()



    fun getDataFromRoom(uuid : Int){

        launch {

            val dao = CountryDatabase(getApplication()).countryDao()

            val country = dao.getCountry(uuid)
            countryLiveData.value = country


        }

    }

}