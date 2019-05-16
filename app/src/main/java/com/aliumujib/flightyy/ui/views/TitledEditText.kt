package com.aliumujib.flightyy.ui.views

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.aliumujib.flightyy.R
import kotlinx.android.synthetic.main.titled_edit_text_layout.view.*
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.textColorResource

/**
 * Created by aliumujib on 02/10/2018.
 */

class TitledEditText : FrameLayout {

    private var view: View
    private var title: TextView
    private var image: ImageView
    private var editText: EditText
    private var linearLayout: LinearLayout

//    private var selectedDrawable: Drawable? = null
//    private var unSelectedDrawable: Drawable? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        view = inflater.inflate(R.layout.titled_edit_text_layout, this, true)

        val a = context.obtainStyledAttributes(attrs, R.styleable.TitledEditText, 0, 0)

        val hint: String? = a.getString(R.styleable.TitledEditText_editTextHint)
        val drawable: Drawable? = a.getDrawable(R.styleable.TitledEditText_editTextImageSrc)
        val titleText: String? = a.getString(R.styleable.TitledEditText_editTextTitle)
        val isPasswordField: Boolean = a.getBoolean(R.styleable.TitledEditText_isPasswordField, false)
        val isEditable: Boolean = a.getBoolean(R.styleable.TitledEditText_isEditable, true)
        val isClickable: Boolean = a.getBoolean(R.styleable.TitledEditText_isClickable, false)

        a.recycle()

        title = view.findViewById(R.id.edit_text_title)
        image = view.findViewById(R.id.hint_image)
        editText = view.findViewById(R.id.edit_text)
        linearLayout = view.findViewById(R.id.linear_layout)

        if (titleText != null) {
            title.text = titleText
        }

        if (drawable != null) {
            image.setImageDrawable(drawable)
        }

        editText.isEnabled = isEditable
        editText.isFocusableInTouchMode = isEditable

        editText.isFocusableInTouchMode = !isClickable


        if (isPasswordField) {
            editText.transformationMethod = PasswordTransformationMethod()
        }

        if (hint != null) {
            editText.hint = hint
        }

        editText.onFocusChangeListener = OnFocusChangeListener { p0, p1 ->
            linearLayout.isActivated = p1
            title.isActivated = p1

            if (drawable != null) {
                if (p1) {
                    setSelectedDrawable()
                } else {
                    setUnSelectedDrawable()
                }
            }
        }

        if (image.drawable != null) {
            setUnSelectedDrawable()
        }
    }

    fun getEditText():EditText{
        return editText
    }

    fun getText(): String {
        return editText.text.toString().trim()
    }

    fun setText(text: String?) {
        editText.setText(text)
    }

    fun setUnSelectedDrawable() {
        var porterDuffColorFilter =
            PorterDuffColorFilter(resources.getColor(R.color.grey_trans), PorterDuff.Mode.MULTIPLY)
        image.drawable.colorFilter = porterDuffColorFilter
        image.setBackgroundColor(Color.TRANSPARENT)
        title.textColorResource = R.color.mds_grey_400
        view.linear_layout.backgroundResource = R.drawable.rectangle_rounder_corners_dark_grey
    }

    fun setSelectedDrawable() {
        var porterDuffColorFilter = PorterDuffColorFilter(resources.getColor(R.color.black), PorterDuff.Mode.MULTIPLY)
        image.drawable.colorFilter = porterDuffColorFilter
        image.setBackgroundColor(Color.TRANSPARENT)
        title.textColorResource = R.color.black
        view.linear_layout.backgroundResource = R.drawable.rectangle_rounder_corners_black
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        title.setOnClickListener(l)
    }

}