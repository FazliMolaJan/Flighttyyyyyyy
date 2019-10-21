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


fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

class NonNullMediatorLiveData<T> : MediatorLiveData<T>()

fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
    val mediator: NonNullMediatorLiveData<T> = NonNullMediatorLiveData()
    mediator.addSource(this, Observer { it?.let { mediator.value = it } })
    return mediator
}

fun <T> NonNullMediatorLiveData<T>.observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}


fun <T, R> LiveData<T>.map(transformation: (T) -> R): LiveData<R> {
    return Transformations.map(this, transformation)
}

fun <A, B, C> LiveData<A>.zipWith(other: LiveData<B>, zipFunc: (A, B) -> C): LiveData<C> {
    return ZippedLiveData<A, B, C>(this, other, zipFunc)
}


fun <T> LiveData<T>.distinct(): LiveData<T> {
    val mediatorLiveData: MediatorLiveData<T> = MediatorLiveData()
    mediatorLiveData.addSource(this) {
        if (it != mediatorLiveData.value) {
            mediatorLiveData.value = it
        }
    }
    return mediatorLiveData
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

class ZippedLiveData<A, B, C>(
    private val ldA: LiveData<A>,
    private val ldB: LiveData<B>,
    private val zipFunc: (A, B) -> C
) : MediatorLiveData<C>() {

    private var lastValueA: A? = null
    private var lastValueB: B? = null

    init {
        addSource(ldA) {
            lastValueA = it
            emitZipped()
        }
        addSource(ldB) {
            lastValueB = it
            emitZipped()
        }
    }

    private fun emitZipped() {
        val valueA = lastValueA
        val valueB = lastValueB
        if (valueA != null && valueB != null) {
            value = zipFunc(valueA, valueB)
        }
    }
}