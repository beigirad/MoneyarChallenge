package ir.beigirad.monenyarchallenge.widget

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v7.widget.AppCompatTextView
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import ir.beigirad.monenyarchallenge.MoneyarApplication


/**
 * Created by farhad-mbp on 3/11/18.
 */
class MyInputLayout : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    private var _hintTv: AppCompatTextView


    init {
        _hintTv = AppCompatTextView(context)
        // typeface set implemented dirty :(
        _hintTv.typeface = MoneyarApplication.getTypeface()
        _hintTv.gravity = Gravity.CENTER
        val layoutParams = LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        _hintTv.layoutParams = layoutParams

        showHint()
    }


    protected fun findEditTextChild(): EditText? {
        return if (childCount > 0 && getChildAt(0) is EditText) {
            getChildAt(0) as EditText
        } else null
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val editText = findEditTextChild()
        if (editText == null) {
            return
        }
        addView(_hintTv)
        showHint()

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.toString().isNullOrBlank())
                    showHint()
                else
                    hideHint()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

    }


    private fun showHint() {
        _hintTv.visibility = View.VISIBLE
    }

    private fun hideHint() {
        _hintTv.visibility = View.GONE
        findEditTextChild()?.hint
    }


    public fun setHint(@StringRes hint: Int, @DrawableRes hintIcon: Int) {
        findEditTextChild()?.setHint("")
        _hintTv.setText(hint)
        _hintTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, hintIcon, 0)
    }

}