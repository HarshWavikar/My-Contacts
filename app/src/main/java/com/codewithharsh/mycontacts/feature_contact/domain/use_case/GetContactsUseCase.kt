package com.codewithharsh.mycontacts.feature_contact.domain.use_case

import com.codewithharsh.mycontacts.feature_contact.domain.model.Contact
import com.codewithharsh.mycontacts.feature_contact.domain.repository.ContactRepository
import com.codewithharsh.mycontacts.feature_contact.domain.util.ContactOrder
import com.codewithharsh.mycontacts.feature_contact.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetContactsUseCase(
    private val contactRepository: ContactRepository
) {

    operator fun invoke(
        contactOrder: ContactOrder = ContactOrder.OrderByFirstName(OrderType.Ascending)
    ): Flow<List<Contact>> {
        return contactRepository.getContacts().map { contacts ->
            when (contactOrder.orderType) {
                OrderType.Ascending -> {
                    when (contactOrder) {
                        is ContactOrder.OrderByFirstName -> {
                                contacts.sortedBy { it.firstName.lowercase() }
                        }
                        is ContactOrder.OrderByLastName -> {
                                    contacts.sortedBy { it.lastName.lowercase() }
                        }
                    }
                }

                OrderType.Descending -> {
                    when (contactOrder) {
                        is ContactOrder.OrderByFirstName -> {
                            contacts.sortedByDescending { it.firstName.lowercase() }
                        }
                        is ContactOrder.OrderByLastName -> {
                            contacts.sortedByDescending { it.lastName.lowercase() }
                        }
                    }
                }
            }
        }
    }
}


