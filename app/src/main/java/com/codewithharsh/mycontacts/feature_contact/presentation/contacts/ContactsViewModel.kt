package com.codewithharsh.mycontacts.feature_contact.presentation.contacts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithharsh.mycontacts.feature_contact.domain.model.Contact
import com.codewithharsh.mycontacts.feature_contact.domain.use_case.ContactUseCase
import com.codewithharsh.mycontacts.feature_contact.domain.util.ContactOrder
import com.codewithharsh.mycontacts.feature_contact.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactUseCase: ContactUseCase
) : ViewModel() {

    // This is the state that contains all the values that our UI will be observing
    private val _state = mutableStateOf(ContactsState())
    val state: State<ContactsState> = _state

    // here I have referenced the recently deleted contact and set it to null
    private var recentlyDeletedContact: Contact? = null

    private var getContactsJob: Job? = null

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted) {
            visiblePermissionDialogQueue.add(0, permission)
        }
    }
    init {
        getContacts(ContactOrder.OrderByFirstName(OrderType.Ascending))
    }

    fun onEvent(event: ContactEvents) {
        when (event) {
            is ContactEvents.DeleteContact -> {
                viewModelScope.launch {
                    contactUseCase.deleteContactUseCase(event.contact)
                    recentlyDeletedContact = event.contact
                }
            }

            is ContactEvents.Order -> {
                if (state.value.contactOrder::class == event.contactOrder::class
                    &&
                    state.value.contactOrder.orderType == event.contactOrder.orderType
                ) {
                    return
                }

                getContacts(event.contactOrder)
            }

            ContactEvents.RestoreContact -> {
                viewModelScope.launch {
                    // here I want to insert the recently deleted contact to the database
                    contactUseCase.insertContactUseCase(recentlyDeletedContact ?: return@launch)
                    recentlyDeletedContact = null
                }
            }

            ContactEvents.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getContacts(contactOrder: ContactOrder) {
        getContactsJob?.cancel()
        getContactsJob = contactUseCase.getContactsUseCase(contactOrder).onEach { contacts ->
            _state.value = state.value.copy(
                contacts = contacts,
                contactOrder = contactOrder
            )
        }.launchIn(viewModelScope)
    }
}


