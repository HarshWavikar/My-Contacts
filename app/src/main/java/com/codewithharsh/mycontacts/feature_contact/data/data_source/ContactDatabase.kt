package com.codewithharsh.mycontacts.feature_contact.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codewithharsh.mycontacts.feature_contact.domain.model.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao
}