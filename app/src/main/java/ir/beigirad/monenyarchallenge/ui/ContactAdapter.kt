package ir.beigirad.monenyarchallenge.ui

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.beigirad.monenyarchallenge.R
import ir.beigirad.monenyarchallenge.data.Contact
import kotlinx.android.synthetic.main.item_contact_wide.view.*

/**
 * Created by farhad-mbp on 3/11/18.
 */
class ContactAdapter(private var contactList: MutableList<Contact>,
                     private var listener: ContactInteract) :
        RecyclerView.Adapter<ContactAdapter.ContactVH>() {
    private val TAG = this.javaClass.simpleName

    var selectedItems = SparseBooleanArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactVH {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.item_contact_wide, parent, false)
        return ContactVH(row)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactVH, position: Int) {
        holder.bind()
    }

    fun toggleSelection(contactId: Int) {
        if (selectedItems.get(contactId, false)) {
            selectedItems.delete(contactId)
        } else {
            selectedItems.put(contactId, true)
        }

        val index = existInList(contactId)
        Log.i(TAG, "toggleSelection $index")
        if (index != -1)
            notifyItemChanged(index)
    }

    private fun existInList(contactId: Int): Int {
        contactList
                .asSequence()
                .forEachIndexed { index, contact ->
                    Log.i(TAG, "checked $index")
                    if (contact.id == contactId)
                        return index
                }
        return -1
    }

    inner class ContactVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.contact_tv_name
        val number = itemView.contact_tv_number
        val img = itemView.contact_img
        val chbox = itemView.contact_chbox

        init {
            chbox.setOnClickListener {
                toggleSelection(contactList[adapterPosition].id)

                if (selectedItems[contactList[adapterPosition].id, false])
                    listener.checkContact(contactList[adapterPosition])
                else
                    listener.uncheckContact(contactList[adapterPosition])
            }
        }

        fun bind() {
            name.text = contactList[adapterPosition].name
            number.text = contactList[adapterPosition].number
            chbox.isChecked = selectedItems[contactList[adapterPosition].id, false]
        }

    }

}