package com.aliumujib.flightyy.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AirportSearchResults(var origin: AirportModel, var destination: AirportModel) : Parcelable