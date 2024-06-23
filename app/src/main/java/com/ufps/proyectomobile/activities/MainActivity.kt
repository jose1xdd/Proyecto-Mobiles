package com.ufps.proyectomobile.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import apis.Task
import com.ufps.proyectomobile.R
import com.ufps.proyectomobile.components.CustomBottomAppBar
import com.ufps.proyectomobile.ui.theme.ToListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToListTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(context = this)
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, context: ComponentActivity) {
    Scaffold(
        topBar = { CustomTopAppBar(modifier) },
        bottomBar = { CustomBottomAppBar(context = context) },
        floatingActionButton = { FloatButton(modifier) }) { innerPadding ->
        IndexList(
            modifier = modifier.padding(innerPadding)
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
fun FloatButton(modifier: Modifier) {
    val context = LocalContext.current
    FloatingActionButton(
        onClick = {
            val intent = Intent(context, NewTaskActivity::class.java)
            context.startActivity(intent)
        },
        modifier = Modifier.padding(top = 20.dp, end = 16.dp)
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}

@Composable
fun IndexList(modifier: Modifier) {
    val context = LocalContext.current
    var tasks = listOf<Task>()/*by remember { mutableStateOf(loadStaticList(context)) }*/
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
    ) {
        items(tasks.size) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 8.dp, horizontal = 16.dp
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = tasks[index].finish, onCheckedChange = { isChecked ->
                        tasks = tasks.toMutableList().apply {
                            this[index] = tasks[index].copy(finish = isChecked)
                        }
                    })
                    Text(
                        tasks[index].title, modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                    }) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}

