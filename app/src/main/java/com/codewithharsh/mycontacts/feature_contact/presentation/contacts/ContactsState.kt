package com.codewithharsh.mycontacts.feature_contact.presentation.contacts

import com.codewithharsh.mycontacts.feature_contact.domain.model.Contact
import com.codewithharsh.mycontacts.feature_contact.domain.util.ContactOrder
import com.codewithharsh.mycontacts.feature_contact.domain.util.OrderType

data class ContactsState(
    val contacts: List<Contact> = emptyList(),
    val contactOrder: ContactOrder = ContactOrder.OrderByFirstName(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false
)


