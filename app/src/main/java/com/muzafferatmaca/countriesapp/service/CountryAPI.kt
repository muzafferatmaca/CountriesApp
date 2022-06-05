package com.muzafferatmaca.countriesapp.service

import com.muzafferatmaca.countriesapp.model.Country
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by Muzaffer Atmaca on 2.06.2022.
 */
interface CountryAPI {

    //https://raw.githubusercontent.com/
    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Country>>


}