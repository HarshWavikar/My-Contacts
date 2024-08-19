package com.codewithharsh.mycontacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.codewithharsh.mycontacts.feature_contact.presentation.add_edit_contact.AddEditContactScreen
import com.codewithharsh.mycontacts.feature_contact.presentation.contacts.ContactsScreen
import com.codewithharsh.mycontacts.feature_contact.presentation.util.Screens
import com.codewithharsh.mycontacts.ui.theme.MyContactsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyContactsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    /* Inorder to implement SharedElementTransition animation between two screens,
                       it's necessary to wrap both the screens in SharedTransitionLayout composable
                       function. */
                    /* Because the screens that sue shared animation, should have access to that
                       sharedTransitionScope, which is why we gonna implement those two screens as
                       an extension functions on SharedTransitionScope.

                       Only composable that are defined within this SharedTransitionScope will be
                       able to access certain modifiers used to mark our shared elements. */

                    SharedTransitionLayout {
                        NavHost(
                            navController = navController,
                            startDestination = Screens.ContactsScreen.route
                        ) {
                            composable(route = Screens.ContactsScreen.route) {
                                ContactsScreen(navController = navController, animatedVisibilityScope = this@composable)
                            }

                            composable(
                                route = Screens.AddEditContactScreen.route
                                        + "?contactId={contactId}",
                                arguments = listOf(navArgument(name = "contactId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                })
                            ) {
                                AddEditContactScreen(navController = navController, animatedVisibilityScope = this@composable)
                            }
                        }
                    }
                }
            }
        }
    }
}


