package com.aliumujib.flightyy.ui.utils.ext

import android.view.Gravity
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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
 * Extesion function
 * **/
fun Fragment.findNavController(): NavController =
    NavHostFragment.findNavController(this)


