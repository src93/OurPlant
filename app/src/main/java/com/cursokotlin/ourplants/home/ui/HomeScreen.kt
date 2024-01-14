package com.cursokotlin.ourplants.home.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cursokotlin.ourplants.interactivedonut.ui.DonutChart
import com.cursokotlin.ourplants.interactivedonut.ui.InteractiveDonutViewModel
import com.cursokotlin.ourplants.interactivedonut.ui.model.DonutChartData
import com.cursokotlin.ourplants.interactivedonut.ui.model.DonutChartDataCollection
import java.text.DecimalFormat

val OxfordBlue = Color(0xFF01184a)
val MetallicYellow = Color(0xFFFFCE08)
val VividOrange = Color(0xFFFD5F00)
val Sapphire = Color(0xFF1259b8)
val RobingEggBlue = Color(0xFF0fd4C4)

val PetroleumGray = Color(0xFF37474f)
val PetroleumLightGray = Color(0xFF455a64)

val itemTextStyle = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
)

val moneyAmountStyle = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Black,
    fontSize = 56.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
)

val viewData = DonutChartDataCollection(
    listOf(
        DonutChartData(1200.0f, Sapphire, title = "Food & Groceries"),
        DonutChartData(1500.0f, RobingEggBlue, title = "Rent"),
        DonutChartData(300.0f, MetallicYellow, title = "Gas"),
        DonutChartData(700.0f, OxfordBlue, title = "Online Purchases"),
        DonutChartData(300.0f, VividOrange, title = "Clothing")
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
                "Fancy Donut Chart",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
        }
    ) { paddingValues ->
        DonutChart(
            modifier = Modifier.padding(paddingValues),
            data = viewData,
            interactiveDonutViewModel = interactiveDonutViewModel
        ) { selected ->
            AnimatedContent(targetState = selected) {
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