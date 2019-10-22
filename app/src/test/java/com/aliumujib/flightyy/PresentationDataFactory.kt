package com.aliumujib.flightyy

import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.models.schedule.Flight
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import com.aliumujib.flightyy.presentation.models.*
import com.aliumujib.flightyy.presentation.models.schedule.FlightModel
import com.aliumujib.flightyy.presentation.models.schedule.ScheduleModel
import konveyor.base.randomBuild

object PresentationDataFactory {



    fun makeAirportModel(): AirportModel {
        return randomBuild()
    }


    fun makeIntegerList(count: Int): List<Int> {
        val categories = mutableListOf<Int>()
        repeat(count) {
            categories.add(randomBuild())
        }
        return categories
    }

    fun makeAirportModelList(count: Int): List<AirportModel> {
        val articles = mutableListOf<AirportModel>()
        repeat(count) {
            articles.add(makeAirportModel())
        }
        return articles
    }


    fun makeScheduleList(count: Int): List<Schedule> {
        val list = mutableListOf<Schedule>()
        repeat(count) {
            list.add(randomBuild())
        }
        return list
    }


    fun makeScheduleModelList(count: Int): List<ScheduleModel> {
        val list = mutableListOf<ScheduleModel>()
        repeat(count) {
            list.add(randomBuild())
        }
        return list
    }


    fun makeFlightList(count: Int): List<Flight> {
        val list = mutableListOf<Flight>()
        repeat(count) {
            list.add(randomBuild())
        }
        return list
    }

    fun makeFlightModelList(count: Int): List<FlightModel> {
        val list = mutableListOf<FlightModel>()
        repeat(count) {
            list.add(randomBuild())
        }
        return list
    }

    fun makeAirportList(count: Int): List<Airport> {
        val mutableList = mutableListOf<Airport>()
        repeat(count) {
            mutableList.add(randomBuild())
        }
        return mutableList
    }

}