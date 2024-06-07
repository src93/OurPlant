@file:OptIn(ExperimentalMaterial3Api::class)

package com.cursokotlin.ourplants.requestnewplan.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cursokotlin.ourplants.R
import com.cursokotlin.ourplants.components.expandablesection.ExpandableSection
import com.cursokotlin.ourplants.requestnewplan.ui.model.ItemResult

@Composable
fun RequestNewPlanScreen(requestNewPlanViewModel: RequestNewPlanViewModel) {
    Content(requestNewPlanViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(requestNewPlanViewModel: RequestNewPlanViewModel) {
    val switchStateActivity by requestNewPlanViewModel.switchStateActivity.observeAsState(false)
    val newActivity by requestNewPlanViewModel.newActivity.observeAsState("")
    val numStars by requestNewPlanViewModel.numStars.observeAsState(1)
    val expandedListActivities by requestNewPlanViewModel.expandedListActivities.observeAsState(
        false
    )
    val selectedActivity by requestNewPlanViewModel.selectedActivity.observeAsState("")
    val description by requestNewPlanViewModel.description.observeAsState("")
    val queryToSearch by requestNewPlanViewModel.queryToSearch.observeAsState("")
    val listPhotos by requestNewPlanViewModel.listPhotos.observeAsState(listOf())
    val selectedPhoto by requestNewPlanViewModel.selectedPhoto.observeAsState(-1)

    Scaffold(
        topBar = {},
        bottomBar = {}
    ) {
        Column(modifier = Modifier.padding(it)) {
            FirstExpandableSection(
                requestNewPlanViewModel,
                newActivity,
                numStars,
                switchStateActivity,
                expandedListActivities,
                selectedActivity,
                description
            )
            SecondExpandableSection(requestNewPlanViewModel, queryToSearch, listPhotos, selectedPhoto)
            RegisterActivityButton() { requestNewPlanViewModel.registerNewPlan() }
        }
    }
}

@Composable
fun FirstExpandableSection(
    requestNewPlanViewModel: RequestNewPlanViewModel,
    newActivity: String,
    numStars: Int,
    switchStateActivity: Boolean,
    expandedListActivities: Boolean,
    selectedActivity: String,
    description: String
) {
    ExpandableSection(title = "Nueva actividad") {
        Column(modifier = Modifier.padding(8.dp)) {
            SwitchSateActivityComponent(switchStateActivity) {
                requestNewPlanViewModel.changeStateActivity()
            }
            TypeActivityContent(
                newActivity = newActivity,
                switchStateActivity = switchStateActivity,
                expandedListActivities,
                selectedActivity,
                requestNewPlanViewModel
            )
            DescriptionContent(description = description) {
                requestNewPlanViewModel.onDescriptionChange(it)
            }
            ListOtters(
                numStars = numStars,
                onClickOtter = { requestNewPlanViewModel.onClickOtter(it) })
        }
    }
}

@Composable
fun SwitchSateActivityComponent(
    switchStateActivity: Boolean,
    onChangeSwitch: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = switchStateActivity,
            modifier = Modifier.padding(end = 8.dp),
            onCheckedChange = {
                onChangeSwitch()
            })
        if (switchStateActivity) {
            Text(text = "Registra un nuevo tipo de actividad")
        } else {
            Text(text = "Selecciona una actividad")
        }
    }
}

@Composable
fun TypeActivityContent(
    newActivity: String,
    switchStateActivity: Boolean,
    expandedListActivities: Boolean,
    selectedActivity: String,
    requestNewPlanViewModel: RequestNewPlanViewModel
) {
    if (switchStateActivity) {
        NewActivityField(newActivity = newActivity) {
            requestNewPlanViewModel.handleOnChangeNewActivityField(it)
        }
    } else {
        DropDownListActivities(
            expandedListActivities = expandedListActivities,
            selectedActivity = selectedActivity,
            onChangeExpandedListMenu = { requestNewPlanViewModel.onChangeExpandedListMenu(it) },
            onSelectedActivity = { requestNewPlanViewModel.onSelectedActivity(it) }
        )
    }
}

@Composable
fun NewActivityField(newActivity: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = newActivity,
        onValueChange = { onValueChange(it) },
        label = { Text(text = "Nueva actividad") },
        shape = RoundedCornerShape(25.dp),
        maxLines = 1,
        singleLine = true
    )
}

@Composable
fun DropDownListActivities(
    expandedListActivities: Boolean,
    selectedActivity: String,
    onChangeExpandedListMenu: (Boolean) -> Unit,
    onSelectedActivity: (String) -> Unit
) {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    ExposedDropdownMenuBox(
        expanded = expandedListActivities,
        onExpandedChange = { onChangeExpandedListMenu(it) }) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier.menuAnchor(),
            value = selectedActivity,
            onValueChange = { },
            label = { Text("Seleccione la actividad") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedListActivities) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expandedListActivities,
            onDismissRequest = { onChangeExpandedListMenu(false) },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = { onSelectedActivity(selectionOption) },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun DescriptionContent(description: String, onDescriptionChange: (String) -> Unit) {
    Text(
        text = "Añade la descripción de la actividad",
        modifier = Modifier.padding(vertical = 8.dp)
    )
    DescriptionField(description = description) {
        onDescriptionChange(it)
    }
}

@Composable
fun DescriptionField(description: String, onDescriptionChange: (String) -> Unit) {
    TextField(
        value = description,
        onValueChange = { onDescriptionChange(it) },
        modifier = Modifier
            .padding(10.dp)
            .height(100.dp)
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
    )
}

@Composable
fun ListOtters(numStars: Int, onClickOtter: (Int) -> Unit) {
    Text(text = "Indica la prioridad de la actividad")
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        OtterImage(numStars = numStars, star = 1, onClickOtter = { onClickOtter(it) })
        OtterImage(numStars = numStars, star = 2, onClickOtter = { onClickOtter(it) })
        OtterImage(numStars = numStars, star = 3, onClickOtter = { onClickOtter(it) })
    }
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        OtterImage(numStars = numStars, star = 4, onClickOtter = { onClickOtter(it) })
        OtterImage(numStars = numStars, star = 5, onClickOtter = { onClickOtter(it) })
        OtterImage(numStars = numStars, star = 6, onClickOtter = { onClickOtter(it) })
    }
}

@Composable
fun OtterImage(numStars: Int, onClickOtter: (Int) -> Unit, star: Int) {
    val modifier = if (star <= numStars) {
        Modifier
            .size(80.dp)
            .border(BorderStroke(width = 2.dp, color = Color.Yellow), CircleShape)
            .clip(shape = CircleShape)
    } else {
        Modifier
            .size(80.dp)
            .clip(shape = CircleShape)
    }
    Image(
        painter = painterResource(id = R.drawable.ic_otter),
        contentDescription = "An otter",
        contentScale = ContentScale.Crop,
        modifier = modifier.clickable { onClickOtter(star) }
    )
}

@Composable
fun SecondExpandableSection(
    requestNewPlanViewModel: RequestNewPlanViewModel,
    queryToSearch: String,
    listPhotos: List<ItemResult>,
    selectedPhoto: Int
) {
    ExpandableSection(title = "Buscador de foto") {
        Column(modifier = Modifier.padding(8.dp)) {
            searchPhotoField(queryToSearch = queryToSearch, requestNewPlanViewModel)
            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
                itemsIndexed(items = listPhotos) {index, item ->
                    itemPhoto(url = item.urls.regular, index, selectedPhoto) {url, index -> requestNewPlanViewModel.onClickPhoto(url = url, index = index) }
                }
            }
        }
    }
}

@Composable
fun searchPhotoField(queryToSearch: String, requestNewPlanViewModel: RequestNewPlanViewModel) {
    TextField(
        value = queryToSearch,
        onValueChange = { requestNewPlanViewModel.onValueChangeSearchInput(it) },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Buscador")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = { requestNewPlanViewModel.searchNewPhotos() })
    )
}

@Composable
fun itemPhoto(url: String, index: Int, selectedItem: Int, onClickPhoto: (url: String, index: Int) -> Unit) {
    val modifier = if (index == selectedItem) {
        Modifier.border(width = 4.dp, color = Color.Red, shape = CircleShape)
    } else {
        Modifier
    }
    Box(modifier = modifier.clickable { onClickPhoto(url, index) }) {
        AsyncImage(model = url, contentDescription = "")
    }
}

@Composable
fun RegisterActivityButton(registerNewPlan: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { registerNewPlan() }, modifier = Modifier.padding(vertical = 8.dp)) {
            Text(text = "Registrar")
        }
    }
}

