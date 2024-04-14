package com.ufps.proyectomobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import apis.loadStaticList
import com.ufps.proyectomobile.ui.theme.ProyectomobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectomobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = { CustomBottomAppBar(modifier) },
    ) { innerPadding ->
        IndexTitle(modifier = modifier.padding(innerPadding))
    }
}


@Composable
fun IndexTitle(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.title),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(20.dp)
        )
        IndexList(modifier)
    }
}

@Composable
fun IndexList(modifier: Modifier) {
    val context = LocalContext.current
    var tasks by remember { mutableStateOf(loadStaticList(context)) }

    LazyColumn(modifier = modifier) {
        items(tasks.size) { index ->
            ListItem(modifier = Modifier.fillMaxWidth(), headlineContent = {
                Text(tasks[index].title)

            }, leadingContent = {
                Checkbox(checked = tasks[index].finish, onCheckedChange = { isChecked ->
                    tasks = tasks.toMutableList().apply {
                        this[index] = tasks[index].copy(finish = isChecked)
                    }
                })
            }, trailingContent = {
                IconButton(
                    onClick = {
                        tasks = tasks.toMutableList().apply {
                            removeAt(index)
                        }
                    }) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = null,
                    )
                }
            }

            )
        }
    }
}


@Composable
fun CustomBottomAppBar(modifier: Modifier = Modifier) {
    BottomAppBar(
        modifier = modifier,
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        Icons.Filled.Menu, contentDescription = "Localized description"
                    )
                }
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = "Localized description",
                    )
                }
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        Icons.Filled.AccountBox,
                        contentDescription = "Localized description",
                    )
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ProyectomobileTheme {
        MainScreen()
    }
}
