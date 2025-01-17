package com.codewithharsh.mycontacts.feature_contact.presentation.add_edit_contact


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codewithharsh.mycontacts.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditContactScreen(
    navController: NavController,
    viewModel: AddEditContactViewModel = hiltViewModel()
) {
    val firstNameState = viewModel.textState.value.firstName
    val lastNameState = viewModel.textState.value.lastName
    val emailState = viewModel.textState.value.email
    val phoneNumberState = viewModel.textState.value.phone

    val snackBarHost = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditContactViewModel.UiEvent.SaveContact -> {
                    navController.navigateUp()
                }

                is AddEditContactViewModel.UiEvent.ShowSnackbar -> {
                    snackBarHost.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditContactEvent.SaveContactClicked)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Add Contact"
                )
            }
        },
        snackbarHost = { SnackbarHost(snackBarHost) }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = firstNameState,
                onValueChange = { firstName ->
                    viewModel.onEvent(AddEditContactEvent.FirstNameChanged(firstName = firstName))
                },
                label = { Text(text = "First Name") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(15.dp),
                maxLines = 1,
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = lastNameState,
                onValueChange = { lastName ->
                    viewModel.onEvent(AddEditContactEvent.LastNameChanged(lastName))
                },
                label = { Text(text = "Last Name") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(15.dp),
                maxLines = 1,
                singleLine = true

            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = emailState,
                onValueChange = { email ->
                    viewModel.onEvent(AddEditContactEvent.EmailChanged(email))
                },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(15.dp),
                maxLines = 1,
                singleLine = true
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = phoneNumberState,
                onValueChange = { phone ->
                    viewModel.onEvent(AddEditContactEvent.PhoneNumberChanged(phone))
                },
                label = { Text(text = "Phone") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                shape = RoundedCornerShape(15.dp),
                maxLines = 1,
                singleLine = true
            )
        }
    }
}


