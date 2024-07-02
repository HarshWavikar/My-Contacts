package com.codewithharsh.mycontacts.feature_contact.domain.use_case

import com.codewithharsh.mycontacts.feature_contact.domain.model.Contact
import com.codewithharsh.mycontacts.feature_contact.domain.repository.ContactRepository

class GetContactUseCase(
    private val contactRepository: ContactRepository
) {
    suspend operator fun invoke(id : Int): Contact? {
        return contactRepository.getContactById(id)
    }
}