package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.BookListScreen
import com.example.myapplication.ui.add_edit_book.AddEditBookScreen
import com.example.myapplication.ui.theme.MVVMBookAppTheme
import com.example.myapplication.utils.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMBookAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.BOOK_LIST
                ) {
                    composable(Routes.BOOK_LIST) {
                        BookListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        route = Routes.ADD_EDIT_BOOK + "?bookId={bookId}",
                        arguments = listOf(
                            navArgument(name = "bookId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditBookScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}