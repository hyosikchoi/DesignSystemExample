package com.hyosik.android.designsystemex

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.appcompat.widget.LinearLayoutCompat
import com.hyosik.android.designsystemex.databinding.DesignButtonBinding

class Button @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet? = null,
    defStyleAttr : Int = 0
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
    private val binding = DesignButtonBinding.inflate(LayoutInflater.from(context),this,true)

    private var buttonType : Type
    private var buttonSize : Size
    private var buttonText: String
    private var leftIcon: Drawable?
    private var rightIcon: Drawable?
    private var isEnabled: Boolean

    private fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()
    private fun Double.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()

    init {
       val typedArray = context.obtainStyledAttributes(attrs,R.styleable.Button)
       buttonType = Type.values().getOrElse(typedArray.getInt(R.styleable.Button_button_type,0)){ Type.PRIMARY }
       buttonSize = Size.values().getOrElse(typedArray.getInt(R.styleable.Button_button_size,0)){ Size.SMALL }
       buttonText = typedArray.getString(R.styleable.Button_button_text) ?: ""
       leftIcon =  typedArray.getDrawable(R.styleable.Button_button_left_icon)
       rightIcon = typedArray.getDrawable(R.styleable.Button_button_right_icon)
       isEnabled = attrs?.getAttributeBooleanValue(xmlns,"enabled",true) ?: true

       /** The recycle() causes the allocated memory to be returned to the available pool immediately and will not stay until garbage collection. */
       typedArray.recycle()


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
        return if(isEnabled) super.onTouchEvent(event) else true
    }

}