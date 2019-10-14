package com.aliumujib.flightyy.data.cache.airlines

import com.aliumujib.flightyy.data.contracts.cache.ICache
import com.aliumujib.flightyy.data.model.AirlineEntity
import javax.inject.Inject
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.nio.charset.Charset


class AirlineCache @Inject constructor(var context: Context) : ICache<AirlineEntity>() {

    private var listOfAirlines = mutableListOf<AirlineEntity>()
    private var mapOfAirlines = mutableMapOf<String, AirlineEntity>()

    init {
        listOfAirlines = getAirlines(context)
        listOfAirlines.forEach {
            mapOfAirlines[it.iata] = it
        }
    }


    override fun fetchAll(): List<AirlineEntity> {
        return listOfAirlines
    }


    private fun getAirlines(context: Context): MutableList<AirlineEntity> {

        var usersList: MutableList<AirlineEntity> = mutableListOf()
        val json = readFromAsset(context, "airlines.json")
        val listType = object : TypeToken<MutableList<AirlineEntity>>() {

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


    override fun get(id: String): AirlineEntity {
        return mapOfAirlines[id]!!
    }

    override fun put(entity: AirlineEntity) {

    }

}