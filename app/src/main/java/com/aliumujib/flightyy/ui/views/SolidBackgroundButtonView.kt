package com.aliumujib.flightyy.ui.views

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.aliumujib.flightyy.R
import com.aliumujib.flightyy.ui.utils.ext.dpToPx
import com.aliumujib.flightyy.ui.utils.ext.mutateColor
import org.jetbrains.anko.padding
import org.jetbrains.anko.textColor

class SolidBackgroundButtonView : AppCompatTextView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {


        val a = context.obtainStyledAttributes(attrs, R.styleable.SolidBackgroundButtonView, 0, 0)

        val btnTextColor: Int? = a.getColor(R.styleable.SolidBackgroundButtonView_textColor, Color.WHITE)
        val backGroundColor: Int? = a.getColor(R.styleable.SolidBackgroundButtonView_backGroundColor, ContextCompat.getColor(context, R.color.colorAccent))

        val drawable = ContextCompat.getDrawable(context, R.drawable.solid_btn_bg)

        backGroundColor?.let {
            drawable?.mutateColor(it)
        }

        background = drawable
        gravity = Gravity.CENTER
        textColor = btnTextColor!!
        padding = context.dpToPx(16)
        setTypeface(typeface, Typeface.BOLD)


        a.recycle()
    }

}