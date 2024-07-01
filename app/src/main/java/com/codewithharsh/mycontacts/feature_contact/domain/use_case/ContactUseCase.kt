package com.codewithharsh.mycontacts.feature_contact.domain.use_case

data class ContactUseCase(
    val getContactsUseCase: GetContactsUseCase,
    val insertContactUseCase: InsertContactUseCase,
    val deleteContactUseCase: DeleteContactUseCase
)
