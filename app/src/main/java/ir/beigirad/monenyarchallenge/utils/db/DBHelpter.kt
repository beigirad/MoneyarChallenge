package ir.beigirad.monenyarchallenge.utils.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import ir.beigirad.monenyarchallenge.MoneyarApplication
import ir.beigirad.monenyarchallenge.data.Contact


/**
 * Created by farhad-mbp on 3/11/18.
 */
object DBHelpter : SQLiteOpenHelper(MoneyarApplication.getAppContext(), "ContactDB", null, 1) {
    private val TAG = this.javaClass.simpleName

    private val tableName = "Contact"
    private val id = "ID"
    private val name = "Name"
    private val phone = "Phone"
    private val img = "Image"

    init {
        Log.i(TAG, "init")
    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.i(TAG, "onCreate")
        val contactQuery = "CREATE TABLE $tableName (" +
                "$id INTEGER PRIMARY KEY," +
                "$name TEXT," +
                "$phone TEXT," +
                "$img TEXT )"
        db?.execSQL(contactQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //  Shame on F**KING OPEN HELPER
    }

    private object Holder {
        val INSTANCE = DBHelpter
    }

    val instance: DBHelpter by lazy { Holder.INSTANCE }

    fun initContacts(list: MutableList<Contact>) {
        val db = writableDatabase
        list.forEach {
            val values = ContentValues()
            values.put(id, it.id)
            values.put(name, it.name)
            values.put(phone, it.number)
            values.put(img, it.img)
            db.insert(tableName, null, values)
        }
    }

    fun allContact(): MutableList<Contact> {
        var contacts = mutableListOf<Contact>()

        val query = "SELECT * FROM $tableName"

        val cursor = readableDatabase.rawQuery(query, null)
        try {
            if (cursor.moveToFirst()) {
                do {
                    val cId = cursor.getInt(cursor.getColumnIndex(id))
                    val cName = cursor.getString(cursor.getColumnIndex(name))
                    val cPhone = cursor.getString(cursor.getColumnIndex(phone))
                    val cImg = cursor.getString(cursor.getColumnIndex(img))

                    contacts.add(Contact(cId, cName, cPhone, cImg))

                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error while trying to get posts from database")
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return contacts
    }

    fun searchContact(query: String): MutableList<Contact> {
        var contacts = mutableListOf<Contact>()

        val query = "SELECT * FROM $tableName WHERE $name LIKE '%$query%' OR $phone LIKE '%$query%'"

        val cursor = readableDatabase.rawQuery(query, null)
        try {
            if (cursor.moveToFirst()) {
                do {
                    val cId = cursor.getInt(cursor.getColumnIndex(id))
                    val cName = cursor.getString(cursor.getColumnIndex(name))
                    val cPhone = cursor.getString(cursor.getColumnIndex(phone))
                    val cImg = cursor.getString(cursor.getColumnIndex(img))

                    contacts.add(Contact(cId, cName, cPhone, cImg))

                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error while trying to get posts from database")
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return contacts
    }

}