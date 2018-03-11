package ir.beigirad.monenyarchallenge.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


/**
 * Created by farhad-mbp on 3/11/18.
 */
object Utils {
    fun showKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (activity.getCurrentFocus() == null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        } else {
            val view = activity.getCurrentFocus()
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity.getCurrentFocus()
        if (view == null) {
            if (inputMethodManager.isAcceptingText())
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0)
        } else {
            if (view is EditText)
                (view as EditText).setText((view as EditText).text.toString()) // reset edit text bug on some keyboards bug
            inputMethodManager.hideSoftInputFromInputMethod(view!!.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

}