package com.codewithharsh.mycontacts.feature_contact.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = -1,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val image: ByteArray? = null
)


class InvalidContactException(message: String) : Exception(message)


