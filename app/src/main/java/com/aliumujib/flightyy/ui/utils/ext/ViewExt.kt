package com.aliumujib.flightyy.ui.utils.ext

import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.aliumujib.flightyy.ui.utils.Event
import com.aliumujib.flightyy.ui.views.TitledEditText
import com.google.android.material.snackbar.Snackbar

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun Fragment.showSnackbar(snackbarText: String, timeLength: Int) {
    activity?.let {
        val snack = Snackbar.make(it.findViewById(android.R.id.content), snackbarText, timeLength)
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        view.layoutParams = params
        snack.show()
    }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun Fragment.setupError(lifecycleOwner: LifecycleOwner, snackbarEvent: LiveData<Event<Int>>, timeLength: Int) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let { res ->
            context?.let { showSnackbar(it.getString(res), timeLength) }
        }
    })
}

fun Fragment.setupStringError(
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
