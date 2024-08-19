package com.codewithharsh.mycontacts.feature_contact.presentation.add_edit_contact

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithharsh.mycontacts.feature_contact.domain.model.Contact
import com.codewithharsh.mycontacts.feature_contact.domain.model.InvalidContactException
import com.codewithharsh.mycontacts.feature_contact.domain.use_case.ContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditContactViewModel @Inject constructor(
    private val contactsUseCase: ContactUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _textState = mutableStateOf(ContactTextFieldState())
    val textState: State<ContactTextFieldState> = _textState

    private val _imageState = mutableStateOf<ByteArray?>(null) // Add image state
    val imageState: State<ByteArray?> = _imageState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentContactId: Int? = null

    init {
        savedStateHandle.get<Int>("contactId")?.let { contactId ->
            if (contactId != -1) {
                viewModelScope.launch {
                    contactsUseCase.getContactUseCase(contactId)?.also { contact ->
                        currentContactId = contact.id
                        _textState.value = textState.value.copy(
                            firstName = contact.firstName,
                            lastName = contact.lastName,
                            email = contact.email,
                            phone = contact.phone
                        )
                        _imageState.value = contact.image
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditContactEvent) {
        when (event) {
            is AddEditContactEvent.FirstNameChanged -> {
                _textState.value = textState.value.copy(
                    firstName = event.firstName
                )
            }

            is AddEditContactEvent.LastNameChanged -> {
                _textState.value = textState.value.copy(
                    lastName = event.lastName
                )
            }

            is AddEditContactEvent.EmailChanged -> {
                _textState.value = textState.value.copy(
                    email = event.email
                )
            }

            is AddEditContactEvent.PhoneNumberChanged -> {
                _textState.value = textState.value.copy(
                    phone = event.phoneNumber
                )
            }

            AddEditContactEvent.SaveContactClicked -> {
                viewModelScope.launch {
                    try {
                        contactsUseCase.insertContactUseCase(
                            Contact(
                                firstName = textState.value.firstName,
                                lastName = textState.value.lastName,
                                email = textState.value.email,
                                phone = textState.value.phone,
                                image = imageState.value,
                                id = currentContactId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveContact)

                    } catch (e: InvalidContactException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Could not save contact"
                            )
                        )
                    }
                }
            }

            is AddEditContactEvent.ImageChanged -> {
                _imageState.value = event.image
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveContact : UiEvent()
    }
}


