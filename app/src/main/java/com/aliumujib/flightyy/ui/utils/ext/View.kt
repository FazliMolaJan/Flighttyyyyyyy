package com.aliumujib.flightyy.ui.utils.ext

import android.view.LayoutInflater
import android.view.View

internal val View.inflater: LayoutInflater get() = LayoutInflater.from(context)
