package com.ufps.proyectomobile.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ufps.proyectomobile.R
import com.ufps.proyectomobile.apis.MainViewModel
import com.ufps.proyectomobile.apis.dtos.User
import com.ufps.proyectomobile.apis.repository.RepositoryImpl
import com.ufps.proyectomobile.storage.SharedPreferencesManager
import com.ufps.proyectomobile.ui.theme.ToListTheme
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {

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
                    LoginScreen(
                        onLoginClick = { (email, token) ->
                            sharedPreferencesManager.saveLoginData(email, token)
                            navigateToMainActivity()
                        },
                        MainViewModel(RepositoryImpl())
                    )
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun LoginScreen(onLoginClick: (Pair<String, String>) -> Unit, mainViewModel: MainViewModel) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val showErrorDialog = remember { mutableStateOf(false) }

    if (showErrorDialog.value) {
        AlertDialog(
            onDismissRequest = { showErrorDialog.value = false },
            title = { Text(text = stringResource(R.string.error_title)) },
            text = { Text(text = stringResource(R.string.invalid_credentials)) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog.value = false }) {
                    Text(text = stringResource(R.string.ok))
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.login_tittle),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = {
                Text(
                    stringResource(R.string.input_login_user),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = {
                Text(
                    stringResource(R.string.input_login_password),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val user = User(emailState.value, passwordState.value)
                        Log.d("USER", user.toString())
                        val token = "Token " + mainViewModel.login(user).token
                        onLoginClick(Pair(emailState.value, token))
                    } catch (e: Exception) {
                        showErrorDialog.value = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.buttom_login),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
