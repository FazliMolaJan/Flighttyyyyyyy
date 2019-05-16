package com.aliumujib.flightyy.data.cache

import android.content.Context
import android.util.Log
import com.aliumujib.flightyy.data.contracts.ICache
import com.aliumujib.flightyy.data.model.AirportEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Inject


open class AirportCache @Inject constructor(var context: Context) : ICache<AirportEntity>() {

    private var listOfAirports = mutableListOf<AirportEntity>()
    private var mapOfAirports = mutableMapOf<String, AirportEntity>()

    init {
        listOfAirports = getAirports(context)
        listOfAirports.forEach {
            mapOfAirports[it.code] = it
        }
    }


    override fun fetchAll(): List<AirportEntity> {
        return listOfAirports
    }


    private fun getAirports(context: Context): MutableList<AirportEntity> {

        var usersList: MutableList<AirportEntity> = mutableListOf()
        val json = readFromAsset(context, "airports.json")
        val listType = object : TypeToken<MutableList<AirportEntity>>() {

        }.type
        // convert json into a list of Airports
        try {
            usersList = Gson().fromJson(json, listType)
        } catch (e: Exception) {
            // we never know :)
            Log.e("error parsing", e.toString())
        }

        return usersList
    }

    /**
     * Read file from asset directory
     * @param act current activity
     * @param fileName file to read
     * @return content of the file, string format
     */
    private fun readFromAsset(act: Context, fileName: String): String {
        var text = ""
        try {
            val `is` = act.assets.open(fileName)

            val size = `is`.available()

            // Read the entire asset into a local byte buffer.
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            text = String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return text
    }


    override fun get(id: String): AirportEntity {
        return mapOfAirports[id]!!
    }

    override fun put(entity: AirportEntity) {
        mapOfAirports[entity.icao] = entity
    }

}