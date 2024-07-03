package com.codewithharsh.mycontacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                    NavHost(
                        navController = navController,
                        startDestination = Screens.ContactsScreen.route
                    ) {

                        composable(route = Screens.ContactsScreen.route) {
                            ContactsScreen(navController = navController)
                        }

                        composable(route = Screens.AddEditContactScreen.route
                                + "?contactId={contactId}",
                            arguments = listOf(navArgument(name = "contactId") {
                                type = NavType.IntType
                                defaultValue = -1
                            })
                        ) {
                            AddEditContactScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
