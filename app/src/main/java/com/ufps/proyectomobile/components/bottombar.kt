package com.ufps.proyectomobile.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ufps.proyectomobile.activities.MainActivity
import com.ufps.proyectomobile.activities.ProfileActivity

@Composable
fun CustomBottomAppBar(context: Context, modifier: Modifier = Modifier) {
    BottomAppBar(
        modifier = modifier,
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                IconButton(onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)

                }) {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = "Home",
                    )
                }
                IconButton(onClick = {
                    val intent = Intent(context, ProfileActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Icon(
                        Icons.Filled.AccountBox,
                        contentDescription = "Account"
                    )
                }
            }
        },
    )
}
