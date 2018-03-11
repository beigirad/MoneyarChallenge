package ir.beigirad.monenyarchallenge.ui

import ir.beigirad.monenyarchallenge.data.Contact

/**
 * Created by farhad-mbp on 3/11/18.
 */
interface ContactInteract {
    fun checkContact(contact: Contact)
    fun uncheckContact(contact: Contact)
}