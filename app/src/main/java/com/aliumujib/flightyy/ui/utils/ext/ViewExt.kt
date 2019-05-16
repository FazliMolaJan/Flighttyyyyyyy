package com.aliumujib.flightyy.ui.utils.ext

import android.os.Build
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aliumujib.flightyy.ui.views.TitledEditText
import com.google.android.material.snackbar.Snackbar
import com.pillr.android.utils.Event

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun Fragment.showSnackbar(snackbarText: String, timeLength: Int) {
    activity?.let { Snackbar.make(it.findViewById<View>(android.R.id.content), snackbarText, timeLength).show() }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun Fragment.setupSnackbar(lifecycleOwner: LifecycleOwner, snackbarEvent: LiveData<Event<Int>>, timeLength: Int) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { res ->
            context?.let { showSnackbar(it.getString(res), timeLength) }
        }
    })
}

fun Fragment.setupStringSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<String>>,
    timeLength: Int
) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { res ->
            context?.let { showSnackbar(res, timeLength) }
        }
    })
}

/**
 * Extesion function
 * **/
fun Fragment.findNavController(): NavController =
    NavHostFragment.findNavController(this)



fun disableViewsClicability(views: Array<View>) {
    views.forEach {
        if (it is EditText) {
            it.isFocusableInTouchMode = false
        } else {
            it.isClickable = false
        }
    }
}

fun enableViewsClicability(views: Array<View>) {
    views.forEach {
        if (it is EditText) {
            it.isFocusableInTouchMode = true
        } else {
            it.isClickable = true
        }
    }
}

fun checkIfViewsIsEmpty(views: Array<TitledEditText>): Boolean {
    views.forEach {
        if (it.getEditText().text.toString().isEmpty()) {
            it.shakyShaky()
            return false
        }
    }

    return true
}


fun checKIfIsLollipop(): Boolean {
    // Check if we're running on Android 5.0 or higher
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}

