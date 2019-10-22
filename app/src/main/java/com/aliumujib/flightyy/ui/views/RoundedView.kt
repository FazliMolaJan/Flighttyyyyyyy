package com.aliumujib.flightyy.ui.views

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.DITHER_FLAG
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.aliumujib.flightyy.R

/**
 * Created by abdulmujibaliu on 9/7/17.
 */

class RoundedView : View {

    internal var paint = Paint(DITHER_FLAG)

    private var uncheckedColor: Int = 0
    private var checkedColor: Int = 0

    private var paintColor = uncheckedColor

    var isChecked = false
        set(checked) {
            field = checked
            paintColor = if (this.isChecked) {
                checkedColor
            } else {
                uncheckedColor
            }
            invalidate()
        }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.RoundedView, 0, 0
        )

        uncheckedColor = typedArray.getColor(
            R.styleable.RoundedView_colorUnselected,
            ContextCompat.getColor(getContext(), R.color.circle_color_default_gray)
        )
        checkedColor =
            typedArray.getColor(R.styleable.RoundedView_colorSelected, ContextCompat.getColor(getContext(), R.color.colorAccent))

        paintColor = uncheckedColor

        typedArray.recycle()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
    }

    protected override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

    protected override fun onDraw(canvas: Canvas) {

        paint.setAntiAlias(true)
        paint.setFilterBitmap(true)
        paint.setDither(true)
        canvas.drawARGB(0, 0, 0, 0)

        paint.setColor(paintColor)
        canvas.drawCircle(
            (getWidth() / 2).toFloat(), (height / 2).toFloat(),
            (width / 2).toFloat(), paint
        )
    }

    fun setCheckedCircleColor(color: Int) {
        this.checkedColor = color
        invalidate()
    }

    fun setUncheckedCircleColor(color: Int) {
        this.uncheckedColor = color
        invalidate()
    }
}