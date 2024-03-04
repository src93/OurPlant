package com.cursokotlin.ourplants.interactivedonut.ui

import android.util.Log
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cursokotlin.ourplants.interactivedonut.ui.model.DonutChartData
import com.cursokotlin.ourplants.interactivedonut.ui.model.DonutChartDataCollection
import com.cursokotlin.ourplants.interactivedonut.ui.theme.DonutChartTheme

@Composable
fun DonutChartWithTheme(
    data: DonutChartDataCollection,
    modifier: Modifier = Modifier,
    chartSize: Dp = 350.dp,
    gapPercentage: Float = 0.04f,
    interactiveDonutViewModel: InteractiveDonutViewModel,
    selectionView: @Composable (selectedItem: DonutChartData?) -> Unit = {}
) {
    DonutChartTheme() {
        DonutChart(
            data = data,
            modifier = modifier,
            chartSize = chartSize,
            gapPercentage = gapPercentage,
            interactiveDonutViewModel = interactiveDonutViewModel,
            selectionView = selectionView
        )
    }
}

@Composable
fun DonutChart(
    data: DonutChartDataCollection,
    modifier: Modifier = Modifier,
    chartSize: Dp = 350.dp,
    gapPercentage: Float = 0.04f,
    interactiveDonutViewModel: InteractiveDonutViewModel,
    selectionView: @Composable (selectedItem: DonutChartData?) -> Unit = {}
) {
    interactiveDonutViewModel.data.value = data
    val selectedIndex by interactiveDonutViewModel.selectedIndex.observeAsState()
    val animationTargetState = (0..data.items.size).map { // VER LUEGO SI LO LLEVO AL VIEWMODEL
        remember { mutableStateOf(DonutChartState()) }
    }
    val animValues = (0..data.items.size).map {
        animateDpAsState(
            targetValue = animationTargetState[it].value.stroke,
            animationSpec = TweenSpec(700)
        )
    }
    val gapAngle = interactiveDonutViewModel.calculateGapAngle(gapPercentage = gapPercentage)
    var center = Offset(0f, 0f)

    Canvas(
        modifier = modifier
            .size(chartSize)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { tapOffset ->
                        interactiveDonutViewModel.handleCanvasTap(
                            center = center,
                            tapOffset = tapOffset,
                            currentStrokeValues = animationTargetState.map { it.value.stroke.toPx() },
                            currentSelectedIndex = selectedIndex!!,
                            onItemSelected = { index ->
                                animationTargetState[index].value = DonutChartState(
                                    DonutChartState.State.Selected
                                )
                            },
                            onItemDeselected = { index ->
                                animationTargetState[index].value = DonutChartState(
                                    DonutChartState.State.Unselected
                                )
                            }
                        )
                    }
                )
            },
        onDraw = {
            val defaultStrokeWidth = STROKE_SIZE_UNSELECTED.toPx()
            center = this.center
            interactiveDonutViewModel.clearAngleList()
            var lastAngle = 0f
            data.items.forEachIndexed { ind, item ->
                val sweepAngle = interactiveDonutViewModel.findSweepAngle(ind, gapPercentage)
                interactiveDonutViewModel.addNewAngle(lastAngle, sweepAngle)
                val strokeWidth = animValues[ind].value.toPx()
                drawArc(
                    color = item.color,
                    startAngle = lastAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(defaultStrokeWidth / 2, defaultStrokeWidth / 2),
                    style = Stroke(strokeWidth, cap = StrokeCap.Butt),
                    size = Size(
                        size.width - defaultStrokeWidth,
                        size.height - defaultStrokeWidth
                    )
                )
                lastAngle += sweepAngle + gapAngle
            }
        }
    )
    selectionView(if (selectedIndex!! >= 0) data.items[selectedIndex!!] else null)
}



