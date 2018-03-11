package ir.beigirad.monenyarchallenge.ui

import android.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import ir.beigirad.monenyarchallenge.R
import ir.beigirad.monenyarchallenge.bases.BaseActivity
import ir.beigirad.monenyarchallenge.data.Contact
import ir.beigirad.monenyarchallenge.utils.db.DBHelpter
import ir.beigirad.monenyarchallenge.widget.CenterToolbar
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.content_main.*
import java.lang.StringBuilder

class MainActivity : BaseActivity(), ContactInteract, FavorateContactIntract {

    override fun remove(contact: Contact) {
        removeFavorite(contact)
        contactAdapter.toggleSelection(contact.id)
    }

    override fun checkContact(contact: Contact) {
        addToFavorite(contact)
    }

    override fun uncheckContact(contact: Contact) {
        removeFavorite(contact)
    }

    override val contentView: Int
        get() = R.layout.activity_main
    override val toolbarTitle: CharSequence?
        get() = getString(R.string.contact_title)
    override val toolbar: CenterToolbar?
        get() = app_toolbar

    var contactList = mutableListOf<Contact>()
    var favoriteList = mutableListOf<Contact>()

    override fun initVariables() {
        super.initVariables()
        retriviFromDB()
    }

    private lateinit var contactAdapter: ContactAdapter
    private lateinit var favorateAdapter: FavorateContactAdapter

    override fun initUI() {
        super.initUI()
        searchbar.setHint(R.string.search_contact, R.drawable.ic_search)

        contact_ry.layoutManager = LinearLayoutManager(this)
        contactAdapter = ContactAdapter(contactList, this)
        contact_ry.adapter = contactAdapter
        contact_ry.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        favorate_ry.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        favorateAdapter = FavorateContactAdapter(favoriteList, this)
        favorate_ry.adapter = favorateAdapter

//        search_et.setOnEditorActionListener { textView, actionId, keyEvent ->
//            when (actionId) {
//                EditorInfo.IME_ACTION_DONE -> {
//                    val query = search_et.text.toString()
//                    if (!query.isNullOrBlank()) {
//                        doSearch(query)
//                        true
//                    }
//                }
//            }
//            false
//        }

        search_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val query = search_et.text.toString()
                doSearch(query)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    private fun retriviFromDB() {
        contactList.clear()
        contactList.addAll(DBHelpter.allContact())
    }

    private fun doSearch(query: String) {
//        it handle internal filter in adapter
//        contactAdapter.filter.filter(query)

        if (query.isNullOrBlank())
            retriviFromDB()
        else {
            contactList.clear()
            contactList.addAll(DBHelpter.searchContact(query))
        }
        contactAdapter.notifyDataSetChanged()

    }

    private fun addToFavorite(contact: Contact) {
        favoriteList.add(contact)
        favorateAdapter.notifyItemInserted(favoriteList.lastIndexOf(contact))
    }

    private fun removeFavorite(contact: Contact) {
        favorateAdapter.notifyItemRemoved(favoriteList.lastIndexOf(contact))
        favoriteList.remove(contact)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_tick -> {
                showResult()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showResult() {
        val selectedItems = contactAdapter.selectedItems
        var ids = StringBuilder()
        for (index in 0 until selectedItems.size()) {
            ids.append("person id : ${selectedItems.keyAt(index)} \n")
        }
        AlertDialog.Builder(this)
                .setTitle("Selected : ${selectedItems.size()} item(s)")
                .setMessage(ids.toString())
                .setPositiveButton("OK", null)
                .create()
                .show()
    }


}
