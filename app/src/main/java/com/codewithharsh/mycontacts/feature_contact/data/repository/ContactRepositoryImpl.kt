package com.codewithharsh.mycontacts.feature_contact.data.repository

import com.codewithharsh.mycontacts.feature_contact.data.data_source.ContactDao
import com.codewithharsh.mycontacts.feature_contact.domain.model.Contact
import com.codewithharsh.mycontacts.feature_contact.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow

class ContactRepositoryImpl(
    private val contactDao: ContactDao
): ContactRepository {
    override fun getContacts(): Flow<List<Contact>> {
       return contactDao.getAllContact()
    }

    override suspend fun getContactById(id: Int): Contact? {
        return contactDao.getContactById(id)
    }

    override suspend fun insertContact(contact: Contact) {
        contactDao.insertContact(contact)
    }

    override suspend fun deleteContact(contact: Contact) {
        contactDao.deleteContact(contact)
    }
}