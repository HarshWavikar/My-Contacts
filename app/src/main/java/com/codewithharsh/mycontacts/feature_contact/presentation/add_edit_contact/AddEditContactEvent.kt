package com.codewithharsh.mycontacts.feature_contact.presentation.add_edit_contact

sealed class AddEditContactEvent {
    data class FirstNameChanged(val firstName: String) : AddEditContactEvent()
    data class LastNameChanged(val lastName: String) : AddEditContactEvent()
    data class EmailChanged(val email: String) : AddEditContactEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : AddEditContactEvent()
    object SaveContactClicked : AddEditContactEvent()
}