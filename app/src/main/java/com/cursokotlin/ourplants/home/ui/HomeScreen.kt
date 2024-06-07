package com.cursokotlin.ourplants.home.ui

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.cursokotlin.ourplants.R
import com.cursokotlin.ourplants.components.bottomappbarnavigation.BottomAppBarNavigation
import com.cursokotlin.ourplants.home.ui.model.KarmaPointModel
import com.cursokotlin.ourplants.interactivedonut.ui.DonutChartWithTheme
import com.cursokotlin.ourplants.interactivedonut.ui.InteractiveDonutViewModel
import com.cursokotlin.ourplants.interactivedonut.ui.model.DonutChartData
import com.cursokotlin.ourplants.interactivedonut.ui.model.DonutChartDataCollection
import com.cursokotlin.ourplants.interactivedonut.ui.theme.PetroleumGray
import com.cursokotlin.ourplants.interactivedonut.ui.theme.PetroleumLightGray
import com.cursokotlin.ourplants.interactivedonut.ui.theme.RobingEggBlue
import com.cursokotlin.ourplants.interactivedonut.ui.theme.Sapphire
import com.cursokotlin.ourplants.interactivedonut.ui.theme.itemTextStyle
import com.cursokotlin.ourplants.interactivedonut.ui.theme.moneyAmountStyle
import kotlinx.coroutines.launch
import java.text.DecimalFormat

private const val MONEY_FORMAT = "###,###,##0.00"
private const val MONEY_FORMAT_NO_CENTS = "###,###,###"

fun Float.toMoneyFormat(
    removeTrailingZeroes: Boolean = false,
): String {
    val format =
        if (removeTrailingZeroes && (this % 1 == 0.0f)) DecimalFormat(MONEY_FORMAT_NO_CENTS)
        else DecimalFormat(MONEY_FORMAT)

    return format.format(this)
}

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    interactiveDonutViewModel: InteractiveDonutViewModel,
    navigate: (String) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState by produceState<HomeUiState>(
        initialValue = HomeUiState.Loading,
        key1 = lifecycle,
        key2 = homeViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            homeViewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is HomeUiState.Error -> {}
        is HomeUiState.Loading -> {
            CircularProgressIndicator()
        }

        is HomeUiState.Success -> {
            val listKarmaPoint = (uiState as HomeUiState.Success).karmaPoints
            Content(interactiveDonutViewModel, homeViewModel, listKarmaPoint, navigate)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun Content(
    interactiveDonutViewModel: InteractiveDonutViewModel,
    homeViewModel: HomeViewModel,
    listKarmaPoint: List<KarmaPointModel>,
    navigate: (String) -> Unit
) {
    val showModal by homeViewModel.showModal.observeAsState()
    Scaffold(
        topBar = {
            Text(
                "Contador de Karma",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
        },
        bottomBar = {
            BottomAppBarNavigation(navigate = navigate)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            DonutChartWithTheme(
                modifier = Modifier
                    .padding(paddingValues)
                    .align(Alignment.Center),
                data = makeDataDonut(listKarmaPoint = listKarmaPoint),
                interactiveDonutViewModel = interactiveDonutViewModel
            ) { selected ->
                AnimatedContent(
                    targetState = selected,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    val amount = it?.amount ?: listKarmaPoint.sumOf { item -> item.karmaPoints }.toFloat()
                    val text = it?.title ?: "Total"

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "$${amount.toMoneyFormat(true)}",
                            style = moneyAmountStyle, color = PetroleumGray
                        )
                        Text(text, style = itemTextStyle, color = PetroleumLightGray)
                    }
                }
            }
            AddTask(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 100.dp, end = 25.dp)
            ) {
                homeViewModel.showViewModal()
            }
            if (showModal == true) {
                Log.i("Sergio", "entra ${homeViewModel.showModal.value}")
                DialogEditKarmaPoints(homeViewModel, listKarmaPoint)
            }
        }
    }
}

@Composable
fun AddTask(modifier: Modifier, onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogEditKarmaPoints(homeViewModel: HomeViewModel, listKarmaPoint: List<KarmaPointModel>) {
    var personSelected by rememberSaveable {
        mutableStateOf("Sergio")
    }
    var switchChecked by rememberSaveable {
        mutableStateOf(true)
    }
    var karmaPoints by rememberSaveable {
        mutableStateOf("")
    }
    val coroutineScope = rememberCoroutineScope()
    Dialog(onDismissRequest = { }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .padding(8.dp), shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Editar karma Points")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = personSelected == "Sergio",
                        onClick = { personSelected = "Sergio" })
                    Text(text = "Sergio")
                    RadioButton(
                        selected = personSelected == "Andrea",
                        onClick = { personSelected = "Andrea" })
                    Text(text = "Andrea")
                }
                Text(text = "Sumar/Restar")
                Row() {
                    Switch(
                        checked = switchChecked,
                        onCheckedChange = { switchChecked = !switchChecked })
                    if (switchChecked) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_happy_person),
                            contentDescription = "Happy",
                            modifier = Modifier.size(40.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_sad_face),
                            contentDescription = "sad",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                Text(text = "Introduce la cantidad")
                TextField(
                    value = karmaPoints,
                    onValueChange = { karmaPoints = it },
                    label = { Text("Introduce los Karma Points") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = {
                        val newKarmaPoint = if (switchChecked) {
                            listKarmaPoint.find { it.username == personSelected }!!.karmaPoints + karmaPoints.toInt()
                        } else {
                            listKarmaPoint.find { it.username == personSelected }!!.karmaPoints - karmaPoints.toInt()
                        }
                        coroutineScope.launch {
                            homeViewModel.updateKarmaPoints(
                                username = personSelected,
                                karmaPoints = newKarmaPoint
                            )
                        }
                    }, modifier = Modifier.padding(3.dp)) {
                        Text(text = "Aceptar")
                    }
                }
            }
        }
    }
}

fun makeDataDonut(listKarmaPoint: List<KarmaPointModel>): DonutChartDataCollection {
    val list = listKarmaPoint.map {
        DonutChartData(
            title = it.username,
            amount = it.karmaPoints.toFloat(),
            color = Sapphire
        )
    }
    return DonutChartDataCollection(items = list)
}

val viewData = DonutChartDataCollection(
    listOf(
        DonutChartData(1200.0f, Sapphire, title = "Sergio"),
        DonutChartData(1500.0f, RobingEggBlue, title = "Andrea's"),
    )
)