package ir.beigirad.monenyarchallenge.utils

import android.content.Context
import ir.beigirad.monenyarchallenge.MoneyarApplication

/**
 * Created by farhad-mbp on 7/26/17.
 */

object SharedPrefUtils {

    private val DB_INITED = "DB_INITED"

    private var userPreferences = MoneyarApplication.getAppContext().getSharedPreferences("UserSettings", Context.MODE_PRIVATE)


    val dbInitialized: Boolean
        get() {
            val inited = userPreferences.getBoolean(DB_INITED, false)
            if (!inited)
                userPreferences.edit().putBoolean(DB_INITED, true).apply()
            return inited
        }
}