package com.ufps.proyectomobile.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ufps.proyectomobile.R
import com.ufps.proyectomobile.apis.MainViewModel
import com.ufps.proyectomobile.apis.dtos.Task
import com.ufps.proyectomobile.apis.dtos.TaskUpdate
import com.ufps.proyectomobile.apis.repository.RepositoryImpl
import com.ufps.proyectomobile.components.CustomBottomAppBar
import com.ufps.proyectomobile.storage.SharedPreferencesManager
import com.ufps.proyectomobile.ui.theme.ToListTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesManager = SharedPreferencesManager(this)
        mainViewModel = MainViewModel(RepositoryImpl())
        val token: String? = sharedPreferencesManager.getToken()

        setContent {
            ToListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        context = this,
                        token = token,
                        mainViewModel = mainViewModel,
                        sharedPreferencesManager = sharedPreferencesManager
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    context: ComponentActivity,
    token: String?,
    mainViewModel: MainViewModel,
    sharedPreferencesManager: SharedPreferencesManager
) {
    val scope = rememberCoroutineScope()
    var tasksState by remember { mutableStateOf<List<Task>>(emptyList()) }

    LaunchedEffect(token) {
        token?.let {
            tasksState = mainViewModel.getTasks(it)
            Log.d("MainScreen", "Tasks received: $tasksState")  // Add log here to check the tasks
        }
    }

    Scaffold(
        topBar = { CustomTopAppBar(modifier) },
        bottomBar = { CustomBottomAppBar(context = context) },
        floatingActionButton = { FloatButton(modifier, context) }
    ) { innerPadding ->
        IndexList(
            modifier = modifier.padding(innerPadding),
            tasks = tasksState,
            onTasksChange = { tasksState = it },
            token,
            mainViewModel,
            sharedPreferencesManager,
            context
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(modifier: Modifier) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(
                text = stringResource(R.string.title),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
    )
}

@Composable
fun FloatButton(modifier: Modifier, context: ComponentActivity) {
    FloatingActionButton(
        onClick = {
            val intent = Intent(context, NewTaskActivity::class.java)
            context.startActivity(intent)
        },
        modifier = modifier.padding(top = 20.dp, end = 16.dp)
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}

@Composable
fun IndexList(
    modifier: Modifier,
    tasks: List<Task>,
    onTasksChange: (List<Task>) -> Unit,
    token: String?,
    mainViewModel: MainViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
    context: ComponentActivity
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
    ) {
        items(tasks) { task ->
            TaskCard(task, tasks, onTasksChange, token, mainViewModel, sharedPreferencesManager, context)
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    tasksState: List<Task>,
    onTasksChange: (List<Task>) -> Unit,
    token: String?,
    mainViewModel: MainViewModel,
    sharedPreferencesManager: SharedPreferencesManager,
    context: ComponentActivity
) {
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.completed,
                onCheckedChange = { isChecked ->
                    val updatedTasks = tasksState.toMutableList().apply {
                        val index = indexOf(task)
                        if (index != -1) this[index] = task.copy(completed = isChecked)
                    }
                    onTasksChange(updatedTasks)
                    scope.launch {
                        token?.let {
                            try {
                                val completed = TaskUpdate(completed = isChecked)
                                mainViewModel.UpdateTaskActivity(it, task.id, completed)
                                Log.d("TaskCard", "Task updated: ${task.id} -> $isChecked")  // Add log here to check the update
                            } catch (e: Exception) {
                                Log.e("TaskCard", "Error updating task", e)
                            }
                        }
                    }
                }
            )
            Text(
                task.name,
                modifier = Modifier
                    .weight(3f)
                    .padding(end = 4.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 1.dp)
            ) {
                IconButton(
                    onClick = {
                        sharedPreferencesManager.saveTaskId(task.id)
                        sharedPreferencesManager.saveTaskTitle(task.name)
                        sharedPreferencesManager.saveTaskDesc(task.description)
                        sharedPreferencesManager.saveTaskPrio(task.priority)
                        val intent = Intent(context, UpdateTaskActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.padding(end = 1.dp)
                ) {
                    Icon(
                        Icons.Filled.Info,
                        contentDescription = null,
                    )
                }
                IconButton(
                    onClick = {
                        scope.launch {
                            token?.let {
                                try {
                                    mainViewModel.deleteTask(it, task.id)
                                    val updatedTasks = tasksState.toMutableList().apply {
                                        remove(task)
                                    }
                                    onTasksChange(updatedTasks)
                                } catch (e: Exception) {
                                    Log.e("TaskCard", "Error deleting task", e)
                                }
                            }
                        }
                    }
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
