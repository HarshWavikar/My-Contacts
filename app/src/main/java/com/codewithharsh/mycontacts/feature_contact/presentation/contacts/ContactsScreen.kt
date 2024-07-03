package com.codewithharsh.mycontacts.feature_contact.presentation.contacts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codewithharsh.mycontacts.feature_contact.presentation.contacts.components.ContactItem
import com.codewithharsh.mycontacts.feature_contact.presentation.contacts.components.OrderSection
import com.codewithharsh.mycontacts.feature_contact.presentation.util.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    navController: NavController,
    viewModel: ContactsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    val snackBarHost = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()  // this will be needed to show the snackbar


    val callPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(
                permission = Manifest.permission.CALL_PHONE,
                isGranted = isGranted
            )
        }
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.AddEditContactScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Contact"
                )
            }
        },
        snackbarHost = {
            SnackbarHost(snackBarHost)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Contacts",
                    style = MaterialTheme.typography.headlineLarge
                )

                IconButton(
                    onClick = {
                        viewModel.onEvent(ContactEvents.ToggleOrderSection)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.SortByAlpha,
                        contentDescription = "Sort Contacts"
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    contactOrders = state.contactOrder,
                    onOrderChange = {
                        viewModel.onEvent(ContactEvents.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(5.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = state.contacts,
                    key = { contact -> contact.id!! }
                ) { contact ->
                    val dismissState = rememberDismissState(
                        confirmValueChange = {
                            if (it == DismissValue.DismissedToStart) {
                                viewModel.onEvent(ContactEvents.DeleteContact(contact))
                                scope.launch {
                                    val result = snackBarHost.showSnackbar(
                                        message = "Contact Deleted",
                                        actionLabel = "Undo",
                                        duration = SnackbarDuration.Short
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(ContactEvents.RestoreContact)
                                    }
                                }
                            }
                            true
                        }
                    )
                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            val color = when (dismissState.dismissDirection) {
                                DismissDirection.EndToStart -> MaterialTheme.colorScheme.error
                                else -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.White
                                )
                            }
                        },
                        dismissContent = {
                            ContactItem(
                                contact = contact,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(Screens.AddEditContactScreen.route + "?contactId=${contact.id}")
                                    },
                                onCallClick = {
                                    if (ContextCompat.checkSelfPermission(
                                            context,
                                            Manifest.permission.CALL_PHONE
                                        ) == PackageManager.PERMISSION_GRANTED
                                    ) {
                                        val intent = Intent(Intent.ACTION_CALL)
                                        intent.data = Uri.parse("tel:${contact.phone}")
                                        context.startActivity(intent)
                                    } else {
                                        callPermissionResultLauncher.launch(
                                            Manifest.permission.CALL_PHONE
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}



