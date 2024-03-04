package com.cursokotlin.ourplants.components.bottomappbarnavigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomAppBarNavigation(goHome: () -> Unit, goNewPlan: () -> Unit, goCheckPlan: () -> Unit) {
    BottomAppBar(containerColor = Color.Blue) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            NavigationItem(icon = Icons.Default.Home, label = "Home") {
                goHome()
            }
            NavigationItem(icon = Icons.Default.Create, label = "New Plan") {
                goNewPlan()
            }
            NavigationItem(icon = Icons.Default.CheckCircle, label = "Check Plan") {
                goCheckPlan()
            }
        }
    }
}

@Composable
private fun NavigationItem(icon: ImageVector, label: String, action: () -> Unit) {
    IconButton(onClick = { action() }) {
        Icon(imageVector = icon, contentDescription = label)
    }
}