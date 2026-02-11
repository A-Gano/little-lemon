package com.example.littlelemon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "little-lemon-db"
        ).build()
    }

    private val repository by lazy {
        MenuRepository(database, NetworkDataSource())
    }

    private val homeViewModel: HomeViewModel by lazy {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(repository) as T
            }
        }
        ViewModelProvider(this, factory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            homeViewModel.loadMenuItems()
        }

        setContent {
            val prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE)
            val start = if (prefs.getString("firstName", "").isNullOrEmpty())
                Onboarding.route else Home.route

            val navController = rememberNavController()

            NavHost(navController, startDestination = start) {
                composable(Onboarding.route) {
                    Onboarding(navController)
                }

                composable(Home.route) {
                    Home(
                        navController = navController,
                        homeViewModel = homeViewModel
                    )
                }

                composable(Profile.route) {
                    Profile(navController)
                }
            }
        }
    }
}