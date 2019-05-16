package com.aliumujib.flightyy

import com.aliumujib.flightyy.data.model.schedule.ScheduleEntity
import com.aliumujib.flightyy.data.model.AirlineEntity
import com.aliumujib.flightyy.data.model.AirportEntity
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.presentation.models.*
import konveyor.base.randomBuild

object PresentationDataFactory {

    fun makeFilm(): FilmModel {
        return randomBuild()
    }

    fun makeSpecie(): SpecieModel {
        return randomBuild()
    }

    fun makePlanet(): PlanetModel {
        return randomBuild()
    }

    fun makeAirportModel(): AirportModel {
        return randomBuild()
    }

    fun makeCharacter(): AirportEntity {
        return randomBuild()
    }

    fun makeIntergerList(count: Int): List<Int> {
        val categories = mutableListOf<Int>()
        repeat(count) {
            categories.add(randomBuild())
        }
        return categories
    }

    fun makeFilmModelList(count: Int): List<FilmModel> {
        val categories = mutableListOf<FilmModel>()
        repeat(count) {
            categories.add(makeFilm())
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

    fun makeDetailedCharacterModel(): DetailedCharacterModel {
        return randomBuild()
    }

    fun makeDetailedCharacter(): ScheduleEntity {
        return randomBuild()
    }

    fun makeFilmList(count: Int): List<AirlineEntity> {
        val categories = mutableListOf<AirlineEntity>()
        repeat(count) {
            categories.add(randomBuild())
        }
        return categories
    }

    fun makeAirportList(count: Int): List<Airport> {
        val mutableList = mutableListOf<Airport>()
        repeat(count) {
            mutableList.add(randomBuild())
        }
        return mutableList
    }

}