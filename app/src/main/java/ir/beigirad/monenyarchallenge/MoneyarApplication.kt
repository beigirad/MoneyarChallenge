package ir.beigirad.monenyarchallenge

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import ir.beigirad.monenyarchallenge.data.Contact
import ir.beigirad.monenyarchallenge.utils.SharedPrefUtils
import ir.beigirad.monenyarchallenge.utils.db.DBHelpter

import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Created by farhad-mbp on 3/11/18.
 */

class MoneyarApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        mContext = applicationContext

        mTypeface = Typeface.createFromAsset(assets, getString(R.string.font_regular))
        mTypefaceBold = Typeface.createFromAsset(assets, getString(R.string.font_bold))


        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.font_regular))
                .setFontAttrId(R.attr.fontPath)
                .build())

        if (!SharedPrefUtils.dbInitialized) {
            var names = resources.getStringArray(R.array.names)
            var list = mutableListOf<Contact>()
            generateSequence(1) { it.inc() }.take(30)
                    .forEach {
                        list.add(Contact(
                                it + 1000,
                                "${names[it.rem(names.size)]} $it",
                                "0911 333 44 ${String.format("%02d", it)}",
                                ""
                        ))
                    }
            DBHelpter.initContacts(list)
        }
    }


    companion object {
        private var mContext: Context? = null
        private var mTypeface: Typeface? = null
        private var mTypefaceBold: Typeface? = null


        fun getAppContext(): Context {
            return mContext!!
        }

        fun getTypeface(): Typeface {
            return mTypeface.let {
                Typeface.createFromAsset(getAppContext().assets, getAppContext().getString(R.string.font_regular))
            }
        }

        fun getBoldTypeface(): Typeface {
            return mTypefaceBold.let {
                Typeface.createFromAsset(getAppContext().assets, getAppContext().getString(R.string.font_bold))
            }
        }
    }

}
