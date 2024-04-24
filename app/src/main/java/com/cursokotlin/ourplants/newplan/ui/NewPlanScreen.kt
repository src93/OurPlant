@file:OptIn(ExperimentalMaterial3Api::class)

package com.cursokotlin.ourplants.newplan.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cursokotlin.ourplants.components.bottomappbarnavigation.BottomAppBarNavigation
import kotlinx.coroutines.launch

@Composable
fun NewPlanScreen() {
    Content()
}

@Composable
fun Content() {
    Scaffold(
        topBar = {
            ListPlanType()
        },
        bottomBar = {
            BottomAppBarNavigation(goHome = { /*TODO*/ }, goNewPlan = { }) {

            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Text(text = "CONTENT")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ListPlanType() {
    val rvState = rememberLazyListState()
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    val coroutineScope = rememberCoroutineScope()
    val listActivities = listOf(
        "Viajes",
        "Restaurantes",
        "Escapadas",
        "Conciertos/Festivales",
        "Actividades"
    )
    LazyRow(
        state = rvState,
        modifier = Modifier.height(50.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(listActivities) {index, item ->
            ItemPlanType(activity = item, isSelected = index == selectedIndex) {
                selectedIndex = index
                coroutineScope.launch {
                    val itemInfo = rvState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
                    if (itemInfo != null) {
                        val center = rvState.layoutInfo.viewportEndOffset / 2
                        val childCenter = itemInfo.offset + itemInfo.size / 2
                        rvState.animateScrollBy((childCenter - center).toFloat())
                    } else {
                        rvState.animateScrollToItem(index)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemPlanType(activity: String, isSelected: Boolean, onClick: () -> Unit) {
    val colorDivider = if (isSelected) {
        Color.Red
    } else {
        Color.Transparent
    }
    Column(modifier = Modifier
        .width(intrinsicSize = IntrinsicSize.Max)
        .fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
        Divider(modifier = Modifier
            .fillMaxWidth()
            .height(0.dp))
        Text(text = "$activity", modifier = Modifier.clickable { onClick() })
        Divider(color = colorDivider, modifier = Modifier
            .fillMaxWidth()
            .height(5.dp))
    }
}

@Composable
fun ListOfPlan() {

}