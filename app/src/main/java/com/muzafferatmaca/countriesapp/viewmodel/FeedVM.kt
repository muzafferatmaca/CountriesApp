package com.muzafferatmaca.countriesapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muzafferatmaca.countriesapp.model.Country
import com.muzafferatmaca.countriesapp.service.CountryAPIService
import com.muzafferatmaca.countriesapp.service.CountryDatabase
import com.muzafferatmaca.countriesapp.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by Muzaffer Atmaca on 2.06.2022.
 */
class FeedVM(application: Application) : BaseViewModel(application) {

    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var customSharedPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 0.1 * 60 * 1000 * 1000 * 1000L


    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {


        val updateTime = customSharedPreferences.getTime()

        if (updateTime != null && updateTime !=0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        }else{
            getDataFromAPI()

        }
    }

    fun refreshFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromSQLite() {

        countryLoading.value = true
        launch {

            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountry(countries)
            Toast.makeText(getApplication(),"countries from sqlite",Toast.LENGTH_LONG).show()
        }


    }

    private fun getDataFromAPI() {
        countryLoading.value = true

        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {

                        storeINSQLite(t)
                        Toast.makeText(getApplication(),"countries from API",Toast.LENGTH_LONG).show()



                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        countryError.value = true
                        countryLoading.value = false
                    }

                })
        )


    }

    private fun showCountry(list : List<Country>){

        countries.value = list
        countryError.value = false
        countryLoading.value = false

    }

    private fun storeINSQLite(list : List<Country>){

        launch {

            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i<list.size){

                list[i].uuid = listLong[i].toInt()
                i += 1

                showCountry(list)

            }
            customSharedPreferences.saveTime(System.nanoTime())
        }

    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }

}