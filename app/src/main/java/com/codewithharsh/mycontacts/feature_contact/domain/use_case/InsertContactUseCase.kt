package com.codewithharsh.mycontacts.feature_contact.domain.use_case

import com.codewithharsh.mycontacts.feature_contact.domain.model.Contact
import com.codewithharsh.mycontacts.feature_contact.domain.model.InvalidContactException
import com.codewithharsh.mycontacts.feature_contact.domain.repository.ContactRepository

class InsertContactUseCase(
    private val contactRepository: ContactRepository
) {
    @Throws(InvalidContactException::class)
    suspend operator fun invoke(contact: Contact) {

        if (contact.firstName.isBlank()) {
            throw InvalidContactException("The first name of the contact can't be empty.")
        }
        if (contact.lastName.isBlank()) {
            throw InvalidContactException("The last name of the contact can't be empty.")
        }
        if (contact.email.isBlank()) {
            throw InvalidContactException("The email of the contact can't be empty.")
        }
        if (contact.phone.isBlank()) {
            throw InvalidContactException("The phone number field can't be empty.")
        }
        contactRepository.insertContact(contact)
    }
}


