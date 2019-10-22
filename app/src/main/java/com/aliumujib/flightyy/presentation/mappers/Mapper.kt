package com.aliumujib.flightyy.presentation.mappers

interface Mapper<D, V> {

    fun mapToView(domain: D): V

    fun mapToDomain(view: V): D
}