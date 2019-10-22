package com.aliumujib.flightyy.data.remote.models

data class LufthansaApiError(
    val ProcessingErrors: ProcessingErrors
):Throwable()

data class ProcessingErrors(
    val ProcessingError: ProcessingError
)

data class ProcessingError(
    val Code: Int,
    val Description: String,
    val InfoURL: String,
    val Type: String
)