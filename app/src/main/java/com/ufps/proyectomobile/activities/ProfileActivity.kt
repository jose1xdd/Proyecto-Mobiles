package com.ufps.proyectomobile.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ufps.proyectomobile.R
import com.ufps.proyectomobile.components.CustomBottomAppBar
import com.ufps.proyectomobile.storage.SharedPreferencesManager
import com.ufps.proyectomobile.ui.theme.ToListTheme

class ProfileActivity : ComponentActivity() {
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferencesManager = SharedPreferencesManager(this)

        setContent {
            ToListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Obtener el user guardado desde SharedPreferences
                    val user = sharedPreferencesManager.getUser() ?: "No user found"
                    val token = sharedPreferencesManager.getToken()
                    Log.d("TOKEN",token.toString())
                    ProfileScreen(
                        onBackClick = { finish() },
                        onLogoutClick = {
                            sharedPreferencesManager.clear()
                            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        },
                        user = user,
                        this
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onBackClick: () -> Unit, onLogoutClick: () -> Unit, user: String,context: ComponentActivity) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { CustomBottomAppBar(context) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Profile Picture
            Image(
                painter = painterResource(id = R.drawable.baseline_account_circle_24),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                colorFilter = null
            )

            // Email Text
            Text(
                text = "Email: $user",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Logout Button
            Button(onClick = onLogoutClick) {
                Text(text = "Logout")
            }
        }
    }
}
