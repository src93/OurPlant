@file:OptIn(ExperimentalMaterial3Api::class)

package com.cursokotlin.ourplants.plans.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cursokotlin.ourplants.R
import com.cursokotlin.ourplants.components.bottomappbarnavigation.BottomAppBarNavigation
import com.cursokotlin.ourplants.nav.ui.Screen
import com.cursokotlin.ourplants.plans.ui.model.PlanItemListModel
import kotlinx.coroutines.launch

@Composable
fun NewPlanScreen(navigate: (String) -> Unit) {
    Content(navigate)
}

@Composable
fun Content(navigate: (String) -> Unit) {
    val listActivities = getItemsMap()
    Scaffold(
        topBar = {
            HeaderList(listActivities, navigate = navigate)
        },
        bottomBar = {
            BottomAppBarNavigation(navigate = navigate)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            ListOfPlan(listActivities.getValue("Viajes"))
        }
    }
}

@Composable
fun HeaderList(listActivities: Map<String, List<PlanItemListModel>>, navigate: (String) -> Unit) {
    val rvState = rememberLazyListState()
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp, start = 4.dp, end = 4.dp)
        ) {
            Text(text = "Actividades por hacer")
            Icon(
                Icons.Filled.AddCircle,
                "Floating action button.",
                modifier = Modifier.clickable { navigate(Screen.RequestNewPlan.route) })
        }

        LazyRow(
            state = rvState,
            modifier = Modifier.height(50.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(listActivities.keys.toList()) { index, item ->
                ItemHeader(activity = item, isSelected = index == selectedIndex) {
                    selectedIndex = index
                    coroutineScope.launch {
                        val itemInfo =
                            rvState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
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
}

@Composable
fun ItemHeader(activity: String, isSelected: Boolean, onClick: () -> Unit) {
    val colorDivider = if (isSelected) {
        Color.Red
    } else {
        Color.Transparent
    }
    Column(
        modifier = Modifier
            .width(intrinsicSize = IntrinsicSize.Max)
            .fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.dp)
        )
        Text(text = "$activity", modifier = Modifier.clickable { onClick() })
        Divider(
            color = colorDivider, modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
    }
}

@Composable
fun ListOfPlan(plans: List<PlanItemListModel>) {
    LazyColumn {
        items(plans) { plan ->
            ItemBody(plan = plan)
        }
    }
}

@Composable
fun ItemBody(plan: PlanItemListModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .padding(end = 4.dp)
    ) {
        leftContentItemBody(plan = plan)
        rightContentItemBody()
    }
}

@Composable
fun leftContentItemBody(plan: PlanItemListModel) {
    Row {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=263af33585f9d32af39d165b000845eb",
            contentDescription = "Translated description of what the image contains"
        )
        Text(text = plan.description)
    }
}

@Composable
fun rightContentItemBody() {
    Column {
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_otter),
                contentDescription = "An otter",
                modifier = Modifier.size(30.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_otter),
                contentDescription = "An otter",
                modifier = Modifier.size(30.dp)
            )
        }
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_otter),
                contentDescription = "An otter",
                modifier = Modifier.size(30.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_otter),
                contentDescription = "An otter",
                modifier = Modifier.size(30.dp)
            )
        }
        Row {
            Image(
                painter = painterResource(id = R.drawable.ic_otter),
                contentDescription = "An otter",
                modifier = Modifier.size(30.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_otter),
                contentDescription = "An otter",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

fun getItemsMap(): Map<String, List<PlanItemListModel>> {
    val viajesItem = listOf(
        PlanItemListModel(
            image = "",
            plan = "Viajes",
            description = "Ir a Croacia",
            stars = 5
        )
    )
    val restaurantesItem = listOf(
        PlanItemListModel(
            image = "",
            plan = "Restaurantes",
            description = "Ir al hundred",
            stars = 2
        )
    )
    val escapadasItem = listOf(
        PlanItemListModel(
            image = "",
            plan = "Escapadas",
            description = "Ir a un hotel burbuja",
            stars = 3
        )
    )
    val conciertosItem = listOf(
        PlanItemListModel(
            image = "",
            plan = "Conciertos",
            description = "Ir a ver a Natos y Waor",
            stars = 4
        )
    )
    val actividadesItem = listOf(
        PlanItemListModel(
            image = "",
            plan = "Actividades",
            description = "Hacer surf en Tenerife",
            stars = 2
        )
    )
    val mapOfPlan = mapOf(
        "Viajes" to viajesItem,
        "Restaurantes" to restaurantesItem,
        "Escapadas" to escapadasItem,
        "Conciertos" to conciertosItem,
        "Actividades" to actividadesItem
    )
    return mapOfPlan
}