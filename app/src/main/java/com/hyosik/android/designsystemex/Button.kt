package com.hyosik.android.designsystemex

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import com.hyosik.android.designsystemex.databinding.DesignButtonBinding
import org.w3c.dom.Text

class Button @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    enum class Type {
        PRIMARY,
        SECONDARY
    }

    enum class Size {
        SMALL,
        MEDIUM,
        LARGE
    }

    private val xmlns = "http://schemas.android.com/apk/res/android"
    private val binding = DesignButtonBinding.inflate(LayoutInflater.from(context), this, true)

    private var buttonType: Type
    private var buttonSize: Size
    private var buttonText: String
    private var leftIcon: Drawable?
    private var rightIcon: Drawable?
    private var isEnabled: Boolean

    private fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()
    private fun Double.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Button)
        buttonType = Type.values()
            .getOrElse(typedArray.getInt(R.styleable.Button_button_type, 0)) { Type.PRIMARY }
        buttonSize = Size.values()
            .getOrElse(typedArray.getInt(R.styleable.Button_button_size, 0)) { Size.SMALL }
        buttonText = typedArray.getString(R.styleable.Button_button_text) ?: ""
        leftIcon = typedArray.getDrawable(R.styleable.Button_button_left_icon)
        rightIcon = typedArray.getDrawable(R.styleable.Button_button_right_icon)
        isEnabled = attrs?.getAttributeBooleanValue(xmlns, "enabled", true) ?: true

        /** The recycle() causes the allocated memory to be returned to the available pool immediately and will not stay until garbage collection. */
        typedArray.recycle()

        setButtonType(buttonType = buttonType)
        setButtonSize(buttonSize = buttonSize)
        setButtonText(buttonText = buttonText)
        setLeftIcon(leftIcon = leftIcon)
        setRightIcon(rightIcon = rightIcon)

        /** icon 및 text 전부 세로 중앙 정렬 */
        gravity = Gravity.CENTER_VERTICAL
    }

    override fun isEnabled(): Boolean {
        return isEnabled
    }

    override fun setEnabled(enabled: Boolean) {
        isEnabled = enabled
        super.setEnabled(enabled)
    }

    /**
     * 터치 이벤트 관련 참조 : https://readystory.tistory.com/185
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (isEnabled) super.onTouchEvent(event) else true
    }


    private fun setButtonType(buttonType: Type) {
        this.buttonType = buttonType
        when (buttonType) {
            Type.PRIMARY -> {
                setBackgroundResource(if (isEnabled) R.drawable.bg_button_type_primary else R.drawable.bg_button_type_primary_disabled)
                binding.tvButton.setTextColor(context.getColor(R.color.white))
                binding.ivLeftIcon.setColorFilter(context.getColor(R.color.white))
                binding.ivRightIcon.setColorFilter(context.getColor(R.color.white))
            }

            Type.SECONDARY -> {
                setBackgroundResource(if (isEnabled) R.drawable.bg_button_type_secondary else R.drawable.bg_button_type_secondary_disabled)
                binding.tvButton.setTextColor(context.getColor(if(isEnabled) R.color.blue_500 else R.color.gray_200))
                binding.ivLeftIcon.setColorFilter(context.getColor(if(isEnabled) R.color.blue_500 else R.color.gray_200))
                binding.ivRightIcon.setColorFilter(context.getColor(if(isEnabled) R.color.blue_500 else R.color.gray_200))
            }
        }
    }
    
    private fun setButtonSize(buttonSize: Size) {
        this.buttonSize = buttonSize
        when(buttonSize) {
            Size.SMALL -> {
                val paddingStart = if (leftIcon != null) 12.dpToPx() else 14.dpToPx()
                val paddingEnd = if (rightIcon != null) 12.dpToPx() else 14.dpToPx()
                val paddingVertical = 6.dpToPx()
                setPadding(paddingStart, paddingVertical, paddingEnd, paddingVertical)
            }
            Size.MEDIUM -> {
                val paddingStart = if (leftIcon != null) 14.dpToPx() else 16.dpToPx()
                val paddingEnd = if (rightIcon != null) 14.dpToPx() else 16.dpToPx()
                val paddingVertical = (7.5).dpToPx()
                setPadding(paddingStart, paddingVertical, paddingEnd, paddingVertical)
            }
            Size.LARGE -> {
                val paddingStart = if (leftIcon != null) 14.dpToPx() else 20.dpToPx()
                val paddingEnd = if (rightIcon != null) 14.dpToPx() else 20.dpToPx()
                val paddingVertical = 13.dpToPx()
                setPadding(paddingStart, paddingVertical, paddingEnd, paddingVertical)
            }
        }
    }

    private fun setButtonText(buttonText: String) {
        this.buttonText = buttonText
        binding.tvButton.text = buttonText
    }

    private fun setLeftIcon(leftIcon: Drawable?) {
        this.leftIcon = leftIcon
        binding.ivLeftIcon.isVisible = leftIcon != null
        binding.ivLeftIcon.setImageDrawable(leftIcon)
    }

    private fun setRightIcon(rightIcon: Drawable?) {
        this.leftIcon = rightIcon
        binding.ivRightIcon.isVisible = rightIcon != null
        binding.ivRightIcon.setImageDrawable(rightIcon)
    }

}