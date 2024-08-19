package com.codewithharsh.mycontacts.feature_contact.presentation.util

sealed class Screens(val route: String) {
    object ContactsScreen : Screens("contacts_screen")
    object AddEditContactScreen : Screens("add_edit_contact_screen")
}


