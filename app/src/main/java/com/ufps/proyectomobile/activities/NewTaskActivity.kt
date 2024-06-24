package com.ufps.proyectomobile.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ufps.proyectomobile.apis.MainViewModel
import com.ufps.proyectomobile.apis.dtos.Task
import com.ufps.proyectomobile.apis.repository.RepositoryImpl
import com.ufps.proyectomobile.components.CustomBottomAppBar
import com.ufps.proyectomobile.storage.SharedPreferencesManager
import com.ufps.proyectomobile.ui.theme.ToListTheme
import kotlinx.coroutines.launch

class NewTaskActivity : ComponentActivity() {
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesManager = SharedPreferencesManager(this)
        mainViewModel = MainViewModel(RepositoryImpl())

        setContent {
            ToListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    NewTaskScreen(onBackClick = { finish() }, this, mainViewModel, sharedPreferencesManager)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskScreen(
    onBackClick: () -> Unit,
    context: ComponentActivity,
    mainViewModel: MainViewModel,
    sharedPreferencesManager: SharedPreferencesManager
) {
    val titleState = remember { mutableStateOf("") }
    val descriptionState = remember { mutableStateOf("") }
    val priorityState = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Success", color = Color.Green) },
            text = { Text(text = "Task created successfully!") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog.value = false
                    onBackClick()
                }) {
                    Text(text = "OK", color = Color.Green)
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "New Task") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                modifier = Modifier.padding(start = 16.dp)
            )
        },
        bottomBar = { CustomBottomAppBar(context) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "New Task", fontSize = 26.sp, fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        value = titleState.value,
                        onValueChange = { titleState.value = it },
                        label = {
                            Text(
                                text = "Task Title",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = descriptionState.value,
                        onValueChange = { descriptionState.value = it },
                        label = {
                            Text(
                                text = "Task Description",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = priorityState.value,
                        onValueChange = { priorityState.value = it },
                        label = {
                            Text(
                                text = "Priority",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            val token = sharedPreferencesManager.getToken()
                            if (token != null) {
                                coroutineScope.launch {
                                    try {
                                        val task = Task(
                                            name = titleState.value,
                                            description = descriptionState.value,
                                            priority = priorityState.value.toInt(),
                                            completed = false
                                        )
                                        val createdTask = mainViewModel.createTask(token, task)
                                        showDialog.value = true
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Error creating task: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(context, "No token found", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Create", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                }
            }
        }
    }
}
