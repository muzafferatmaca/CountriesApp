package com.muzafferatmaca.countriesapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.muzafferatmaca.countriesapp.model.Country

/**
 * Created by Muzaffer Atmaca on 3.06.2022.
 */

@Dao
interface CountryDao {

    @Insert
    suspend fun insertAll(vararg countries: Country): List<Long>

    @Query("SELECT * FROM country")
    suspend fun getAllCountries(): List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId: Int): Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

}