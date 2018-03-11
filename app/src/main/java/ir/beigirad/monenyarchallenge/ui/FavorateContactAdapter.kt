package ir.beigirad.monenyarchallenge.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.beigirad.monenyarchallenge.R
import ir.beigirad.monenyarchallenge.data.Contact
import kotlinx.android.synthetic.main.item_contact_square.view.*

/**
 * Created by farhad-mbp on 3/11/18.
 */
class FavorateContactAdapter(var contactList: MutableList<Contact>, var listener: FavorateContactIntract) : RecyclerView.Adapter<FavorateContactAdapter.ContactVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactVH {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.item_contact_square, parent, false)
        return ContactVH(row)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactVH, position: Int) {
        holder.bind()
    }


    inner class ContactVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.contact_tv_name
        val remove = itemView.contact_remove
        val img = itemView.contact_img


        init {
            remove.setOnClickListener {
                listener.remove(contactList[adapterPosition])
            }
        }

        fun bind() {
            name.text = contactList[adapterPosition].name
        }

    }

}