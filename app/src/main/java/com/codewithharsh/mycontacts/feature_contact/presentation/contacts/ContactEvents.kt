package com.codewithharsh.mycontacts.feature_contact.presentation.contacts

import com.codewithharsh.mycontacts.feature_contact.domain.model.Contact
import com.codewithharsh.mycontacts.feature_contact.domain.util.ContactOrder

sealed class ContactEvents {
    data class Order(val contactOrder: ContactOrder) : ContactEvents()
    data class DeleteContact(val contact: Contact) : ContactEvents()
    data object RestoreContact : ContactEvents()
    data object ToggleOrderSection : ContactEvents()
}


