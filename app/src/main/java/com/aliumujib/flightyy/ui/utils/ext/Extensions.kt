package com.aliumujib.flightyy.ui.utils.ext

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.*
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.backgroundDrawable
import java.util.*
import java.util.concurrent.TimeUnit
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.core.os.ConfigurationCompat
import com.aliumujib.flightyy.R
import com.google.android.material.snackbar.Snackbar
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created by aliumujib on 09/06/2018.
 */


fun View.shakyShaky() {
    val animShake = AnimationUtils.loadAnimation(this.context, R.anim.shake)
    val v = this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
    if (SDK_INT >= Build.VERSION_CODES.O) {
        v!!.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        v!!.vibrate(500)
    }
    this.startAnimation(animShake)
}


fun Context.dpToPx(dp: Int): Int {
    var displayMetrics = resources.displayMetrics
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun Any.delayForASecond(function: () -> Unit) {
    val handler = Handler()
    handler.postDelayed({
        function.invoke()
    }, 1000)
}

fun ViewGroup.inflate(layout: Int): View {
    val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    return layoutInflater.inflate(layout, this, false)
}

fun Drawable.mutateColor(colorInt: Int) {
    try {
        var porterDuffColorFilter = PorterDuffColorFilter(colorInt, PorterDuff.Mode.MULTIPLY)
        this.colorFilter = porterDuffColorFilter
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
