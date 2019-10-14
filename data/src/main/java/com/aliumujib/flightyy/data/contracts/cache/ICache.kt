package com.aliumujib.flightyy.data.contracts.cache


abstract class ICache<E> {

    val hashMap = HashMap<Int, E>()

    abstract fun get(id: String): E

    abstract fun fetchAll(): List<E>

    abstract fun put(entity: E)

}