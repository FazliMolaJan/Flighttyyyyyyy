package com.aliumujib.flightyy.ui.utils.ext

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.aliumujib.flightyy.ui.utils.LoadingDialog

internal val View.inflater: LayoutInflater get() = LayoutInflater.from(context)

fun Fragment.showLoadingDialog() {
    val ft = this.childFragmentManager.beginTransaction()
    val dialogFragment = LoadingDialog()
    dialogFragment.show(ft, "loading_dialog")
}

fun Fragment.hideLoadingDialog() {
    val fragment = this.childFragmentManager.findFragmentByTag("loading_dialog")
    fragment?.let {
        (it as LoadingDialog).dismiss()
    }
}