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

fun Activity.dpToPx(dp: Int): Int {
    var displayMetrics = resources.displayMetrics
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun Context.getColorHexString(@ColorRes resId: Int): String {
    val colorInt = ContextCompat.getColor(this, resId)
    return String.format("#%06X", 0xFFFFFF and colorInt)
}


fun RecyclerView.removeAllDecorations() {
    while (this.itemDecorationCount > 0) {
        this.removeItemDecoration(this.getItemDecorationAt(0))
    }
}


//fun Toolbar.hide() {
//    this.animate()
//        .translationY(0f)
//        .alpha(0.0f)
//        .setListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
//                super.onAnimationEnd(animation);
//                visibility = View.GONE
//            }
//        });
//}
//
//
//fun Toolbar.show() {
//    visibility = View.VISIBLE
//    alpha = 0.0f
//
//    this.animate()
//        .translationY(this.height.toFloat())
//        .alpha(1.0f)
//        .setListener(null)
//}

fun View.shakyShaky() {
    val animShake = AnimationUtils.loadAnimation(this.context, R.anim.shake)
    val v = this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        v!!.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        v!!.vibrate(500)
    }
    this.startAnimation(animShake)
}

fun View.hide() {
    visibility = View.GONE
}

fun View.hideAndLeaveSpace() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun Int.randInt(min: Int, max: Int): Int {
    val rand = Random()
    return rand.nextInt(max - min + 1) + min
}

fun Context.dpToPx(dp: Int): Int {
    var displayMetrics = resources.displayMetrics
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun <E> List<E>.random(random: java.util.Random): E? = if (size > 0) get(random.nextInt(size)) else null

fun <E> List<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null

fun Fragment.dpToPx(dp: Int): Int = activity!!.dpToPx(dp)

fun View.setMargins(
    left: Int? = null,
    top: Int? = null,
    right: Int? = null,
    bottom: Int? = null
) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams
        ?: return
    lp.setMargins(
        left ?: lp.leftMargin,
        top ?: lp.topMargin,
        right ?: lp.rightMargin,
        bottom ?: lp.rightMargin
    )
    layoutParams = lp
}


fun View.setPaddings(
    left: Int = this.paddingLeft,
    top: Int = this.paddingTop,
    right: Int = this.paddingRight,
    bottom: Int = this.paddingBottom
) {
    this.setPadding(left, top, right, bottom)
}

fun Any.delay(function: () -> Unit, timeMillis: Long = 1000) {
    val handler = Handler()
    handler.postDelayed({
        function.invoke()
    }, timeMillis)
}


fun Any.delayForASecond(function: () -> Unit) {
    val handler = Handler()
    handler.postDelayed({
        function.invoke()
    }, 1000)
}

fun Fragment.appCompatActivity(): AppCompatActivity {
    return activity as AppCompatActivity
}

fun String.toCamelCase(): String {
    val parts = this.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    var camelCaseString = ""
    parts.forEachIndexed { index, part ->
        camelCaseString += if (index == 0) {
            part.capitalize()
        } else {
            " ${part.capitalize()}"
        }
    }
    return camelCaseString
}


fun String.isAvailable(): Boolean {
    return this != "N/A"
}

fun MutableLiveData<Void>.call() {
    value = null
}


fun View.hideKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Fragment.hideKeyBoard() {
    val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view!!.windowToken, 0)
}

fun AppCompatActivity.hideSystemUI() {

    val uiOptions = window.decorView.systemUiVisibility
    var newUiOptions = uiOptions
    val isImmersiveModeEnabled = if (SDK_INT >= Build.VERSION_CODES.KITKAT) {
        uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY == uiOptions
    } else {
        TODO("VERSION.SDK_INT < KITKAT")
    }
    if (isImmersiveModeEnabled) {
        Log.i(this::class.java.canonicalName, "Turning immersive mode mode off. ")
    } else {
        Log.i(this::class.java.canonicalName, "Turning immersive mode mode on.")
    }

    // Navigation bar hiding:  Backwards compatible to ICS.
    if (SDK_INT >= JELLY_BEAN_MR1) {
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    // Status bar hiding: Backwards compatible to Jellybean
    if (SDK_INT >= JELLY_BEAN_MR1) {
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    // Immersive mode: Backward compatible to KitKat.
    // Note that this flag doesn't do anything by itself, it only augments the behavior
    // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
    // all three flags are being toggled together.
    // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
    // Sticky immersive mode differs in that it makes the navigation and status bars
    // semi-transparent, and the UI flag does not get cleared when the user interacts with
    // the screen.
    if (SDK_INT >= 18) {
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    window.decorView.systemUiVisibility = newUiOptions
}

fun String.isNotAvailable(): Boolean {
    return this == "N/A"
}

fun AppCompatActivity.showSystemUI() {
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
}

fun Activity.setTransparentStatusBar() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = Color.TRANSPARENT
    }
}


fun Activity.setStatusBarColorAsPrimaryDark() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = ContextCompat.getColor(this, R.color.grey_trans)
    }
}

fun manipulateColor(color: Int, factor: Float): Int {
    val a = Color.alpha(color)
    val r = Math.round(Color.red(color) * factor)
    val g = Math.round(Color.green(color) * factor)
    val b = Math.round(Color.blue(color) * factor)
    return Color.argb(
        a,
        Math.min(r, 255),
        Math.min(g, 255),
        Math.min(b, 255)
    )
}

fun View.colorBackground(@ColorRes colorRes: Int, context: Context) {
    var porterDuffColorFilter =
        PorterDuffColorFilter(ContextCompat.getColor(context, colorRes), PorterDuff.Mode.MULTIPLY)
    this.backgroundDrawable?.colorFilter = porterDuffColorFilter
}

fun Drawable.mutateColor(colorCode: String) {
    try {
        var integerColor = Color.parseColor(colorCode)
        var porterDuffColorFilter = PorterDuffColorFilter(integerColor, PorterDuff.Mode.MULTIPLY)
        this.colorFilter = porterDuffColorFilter
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun Any.getRelativeDateUtils(now: Long, past: Long): String {
    var seconds = TimeUnit.MILLISECONDS.toSeconds(now - past)
    var minutes = TimeUnit.MILLISECONDS.toMinutes(now - past)
    var hours = TimeUnit.MILLISECONDS.toHours(now - past)
    var days = TimeUnit.MILLISECONDS.toDays(now - past)

    return when {
        seconds < 60 -> "$seconds seconds ago"
        minutes < 60 -> "$minutes minutes ago"
        (hours < 2) -> "$hours hour ago"
        (hours in 2..23) -> "$hours hours ago"
        else -> "$days days ago"
    }
}

fun Any.getColorForPositionInList(position: Int): String {
    return getColorArray()[position % 5]
}

fun Any.getColorArray(): List<String> {
    return mutableListOf("#8E4AC3", "#F15642", "#0077FF", "#FBBC06", "#37C5EF", "#34A853")
}

inline val @receiver:ColorInt Int.darken
    @ColorInt
    get() = ColorUtils.blendARGB(this, Color.BLACK, 0.2f)

//TAG in the companion object for fragments  exists because https://discuss.kotlinlang.org/t/static-extension-methods-for-java-classes/2190
fun Fragment.TAG(): String = javaClass.canonicalName


fun ViewGroup.inflate(layout: Int): View {
    val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    return layoutInflater.inflate(layout, this, false)

}

fun Activity.showSnackbar(
    message: String,
    length: Int = Snackbar.LENGTH_SHORT,
    actionString: String = "",
    `listener`: View.OnClickListener? = null
) {
    val snackbar = Snackbar.make(findViewById(android.R.id.content), message, length)

    if (actionString.isNotEmpty()) {
        snackbar.setAction(actionString, `listener`)
    }
    snackbar.show()
}

fun String.getFormattedNumber(pattern: String): String {
    val formattedString = this.removeDashes()
    val patternsLength = this.getPattern(pattern)
    var subStringList = mutableListOf<String>()

    var beginIndex = 0
    var endIndex = patternsLength[0]
    for (i in 0 until patternsLength.size) {
        if (endIndex > formattedString.length) {
            subStringList.add(formattedString.substring(beginIndex, formattedString.length))
            break
        }
        if (formattedString.length >= endIndex) {
            subStringList.add(formattedString.substring(beginIndex, endIndex))
        }

        beginIndex = endIndex
        if (i + 1 == patternsLength.size) break
        endIndex += patternsLength[i + 1]
    }

    var subString = ""
    subStringList.forEachIndexed { index, s ->
        subString = if (index == subStringList.size - 1) "$subString$s"
        else "$subString$s-"
    }

    var nsubString = ""
    var nendIndex = patternsLength[0]
    for (i in 0 until subStringList.size) {
        nsubString = if (formattedString.length > nendIndex) {
            "$nsubString${subStringList[i]}-"
        } else {
            "$nsubString${subStringList[i]}"
        }

        if (i + 1 == subStringList.size) break
        nendIndex += patternsLength[i + 1]
    }
    return nsubString
}

fun String.getPattern(pattern: String): List<Int> {
    val count = pattern.length - pattern.replace("-", "").length
    val patternsLength = mutableListOf<Int>()

    var index = pattern.indexOf('-')
    var length = pattern.substring(0, index).length
    for (i in 0..count) {
        patternsLength.add(length)
        length = index + 1
        try {
            index = pattern.indexOf('-', index + 1)
            length = pattern.substring(length, index).length
        } catch (e: StringIndexOutOfBoundsException) {
            length = pattern.substring(length, pattern.length).length
        }
    }
    return patternsLength
}

fun String.removeDashes(): String {
    return this.replace("-", "")
}

fun String.removeCommas(): String {
    return this.replace(",", "-")
}

fun Context.readJsonFromFilePath(path: String): String {
    var line: String? = ""
    try {
        val inputStream: InputStream = assets.open(path)
        val inputStreamReader = InputStreamReader(inputStream)
        val sb = StringBuilder()
        val br = BufferedReader(inputStreamReader)
        line = br.readLine()
        while (br.readLine() != null) {
            sb.append(line)
            line = br.readLine()
        }
        br.close()
    } catch (e: Exception) {
        Log.d("tag", e.toString())
    }
    return line ?: ""
}


fun View.clickWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}


fun Context.getCurrentLocale(): Locale {
    return ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
}

fun Drawable.mutateColor(colorInt: Int) {
    try {
        var porterDuffColorFilter = PorterDuffColorFilter(colorInt, PorterDuff.Mode.MULTIPLY)
        this.colorFilter = porterDuffColorFilter
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


fun Activity.setStatusBarColor(@ColorRes color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = ContextCompat.getColor(this, color)
    }
}
