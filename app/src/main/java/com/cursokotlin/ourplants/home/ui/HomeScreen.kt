package com.cursokotlin.ourplants.home.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cursokotlin.ourplants.components.bottomappbarnavigation.BottomAppBarNavigation
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
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.text.DecimalFormat

val viewData = DonutChartDataCollection(
    listOf(
        DonutChartData(1200.0f, Sapphire, title = "Sergio"),
        DonutChartData(1500.0f, RobingEggBlue, title = "Andrea's"),
    )
)

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
fun HomeScreen(interactiveDonutViewModel: InteractiveDonutViewModel) {
    Content(interactiveDonutViewModel)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun Content(interactiveDonutViewModel: InteractiveDonutViewModel) {
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
            BottomAppBarNavigation(goHome = { /*TODO*/ }, goNewPlan = { /*TODO*/ }) {
                
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            DonutChartWithTheme(
                modifier = Modifier
                    .padding(paddingValues)
                    .align(Alignment.Center),
                data = viewData,
                interactiveDonutViewModel = interactiveDonutViewModel
            ) { selected ->
                AnimatedContent(targetState = selected, modifier = Modifier.align(Alignment.Center)) {
                    val amount = it?.amount ?: viewData.totalAmount
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
        }
    }
}