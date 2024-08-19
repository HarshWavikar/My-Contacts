package com.codewithharsh.mycontacts.feature_contact.domain.use_case

import com.codewithharsh.mycontacts.feature_contact.domain.model.Contact
import com.codewithharsh.mycontacts.feature_contact.domain.repository.ContactRepository

class DeleteContactUseCase(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke(contact: Contact) {
        contactRepository.deleteContact(contact)
    }
}

