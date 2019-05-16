package com.aliumujib.flightyy.presentation.state

class Resource<out T> constructor(val status: Status,
                                  val data: T?,
                                  val message: String?) {

    fun <T> success(data: T): Resource<T> {
        return Resource(Status.SUCCESS, data, null)
    }

    fun <T> error(message: String?): Resource<T> {
        return Resource(Status.ERROR, null, message)
    }

    fun <T> loading(): Resource<T> {
        return Resource(Status.LOADING, null, null)
    }

}