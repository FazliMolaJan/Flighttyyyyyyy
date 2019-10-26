package com.aliumujib.flightyy.ui.utils

import androidx.lifecycle.*

/**
 *
 * Plenty copy and paste coding made possible by
 *
 * Random gist page
 * https://gist.github.com/Sloy/7a267237f7bc27a2057be744209c1c61
 *
 *
 * Antonio Leiva
 * https://antonioleiva.com/architecture-components-kotlin/
 * */


fun <T, R> LiveData<T>.map(transformation: (T) -> R): LiveData<R> {
    return Transformations.map(this, transformation)
}


fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}
