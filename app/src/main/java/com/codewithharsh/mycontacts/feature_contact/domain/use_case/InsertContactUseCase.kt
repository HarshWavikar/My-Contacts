package com.codewithharsh.mycontacts.feature_contact.domain.use_case

import com.codewithharsh.mycontacts.feature_contact.domain.model.Contact
import com.codewithharsh.mycontacts.feature_contact.domain.repository.ContactRepository

class InsertContactUseCase(
    private val contactRepository: ContactRepository
) {
    suspend fun invoke(contact: Contact) {
        contactRepository.insertContact(contact)
    }
}