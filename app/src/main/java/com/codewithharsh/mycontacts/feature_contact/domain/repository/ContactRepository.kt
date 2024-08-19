package com.codewithharsh.mycontacts.feature_contact.domain.repository

import com.codewithharsh.mycontacts.feature_contact.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun getContacts(): Flow<List<Contact>>
    suspend fun getContactById(id: Int): Contact?
    suspend fun insertContact(contact: Contact)
    suspend fun deleteContact(contact: Contact)
}

