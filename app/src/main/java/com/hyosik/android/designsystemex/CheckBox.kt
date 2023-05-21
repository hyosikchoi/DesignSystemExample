package com.hyosik.android.designsystemex

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox

class CheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatCheckBox(
    context, attrs, defStyleAttr
) {
    enum class Size {
        SMALL,
        MEDIUM,
        LARGE
    }

    private val xmlns = "http://schemas.android.com/apk/res/android"

    private fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density)
    private fun Double.dpToPx() = (this * Resources.getSystem().displayMetrics.density)

    private lateinit var checkBoxSize: Size

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CheckBox)
        isEnabled = attrs?.getAttributeBooleanValue(xmlns, "enabled", true) ?: true
        setCheckBoxSize(
            Size.values()
                .getOrElse(typedArray.getInt(R.styleable.CheckBox_checkbox_size, 0)) { Size.SMALL })
        setTextColor(context.getColor(if (isEnabled) R.color.gray_900 else R.color.gray_200))

        /** The recycle() causes the allocated memory to be returned to the available pool immediately and will not stay until garbage collection. */
        typedArray.recycle()

        includeFontPadding = false
        letterSpacing = -0.02f
        setLineSpacing(3.dpToPx(), 1.0f)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        setTextColor(context.getColor(if (enabled) R.color.gray_900 else R.color.gray_200))
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        if (::checkBoxSize.isInitialized) setCheckBoxSize(checkBoxSize)
    }

    private fun setCheckBoxSize(checkBoxSize: Size) {
        this.checkBoxSize = checkBoxSize
        when (checkBoxSize) {
            Size.SMALL -> {
                minHeight = 14.dpToPx().toInt()
                minimumHeight = 14.dpToPx().toInt()
            }

            Size.MEDIUM -> {

            }

            Size.LARGE -> {

            }
        }
    }

}