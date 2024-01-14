package com.cursokotlin.ourplants.interactivedonut.ui

import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursokotlin.ourplants.interactivedonut.ui.model.DonutChartDataCollection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt

@HiltViewModel
class InteractiveDonutViewModel @Inject constructor(): ViewModel() {
    var data = MutableLiveData<DonutChartDataCollection>()
    private val anglesList = mutableListOf<DrawingAngles>()
    private val _selectedIndex = MutableLiveData(-1)
    val selectedIndex = _selectedIndex

    /**
     * Calculate the sweep angle of an arc including the gap as well. The gap is derived based
     * on [gapPercentage].
     */
    fun calculateGapAngle(gapPercentage: Float): Float {
        val gap = calculateGap(gapPercentage)
        val totalAmountWithGap = getTotalAmountWithGapIncluded(gapPercentage)

        return (gap / totalAmountWithGap) * TOTAL_ANGLE
    }

    /**
     * Returns the sweep angle of a given point in the [DonutChartDataCollection]. This calculations
     * takes the gap between arcs into the account.
     */
    fun findSweepAngle(
        index: Int,
        gapPercentage: Float
    ): Float {
        val amount = data.value!!.items[index].amount
        val gap = calculateGap(gapPercentage)
        val totalWithGap = getTotalAmountWithGapIncluded(gapPercentage)
        val gapAngle = calculateGapAngle(gapPercentage)
        return ((((amount + gap) / totalWithGap) * TOTAL_ANGLE)) - gapAngle
    }

    fun clearAngleList() {
        anglesList.clear()
    }

    fun addNewAngle(lastAngle: Float, sweepAngle: Float) {
        anglesList.add(DrawingAngles(start = lastAngle, end = sweepAngle))
    }

    fun handleCanvasTap(
        center: Offset,
        tapOffset: Offset,
        currentStrokeValues: List<Float>,
        currentSelectedIndex: Int,
        onItemSelected: (Int) -> Unit = {},
        onItemDeselected: (Int) -> Unit = {},
    ) {
        val normalized = tapOffset.findNormalizedPointFromTouch(center)
        val touchAngle =
            calculateTouchAngleAccordingToCanvas(center, normalized)
        val distance = findTouchDistanceFromCenter(center, normalized)

        var selectedIndex = -1
        var newDataTapped = false

        anglesList.forEachIndexed { ind, angle ->
            val stroke = currentStrokeValues[ind]
            if (angle.isInsideAngle(touchAngle)) {
                if (distance > (center.x - stroke) &&
                    distance < (center.x)
                ) { // since it's a square center.x or center.y will be the same
                    selectedIndex = ind
                    newDataTapped = true
                }
            }
        }

        if (selectedIndex >= 0 && newDataTapped) {
            _selectedIndex.value = selectedIndex
            onItemSelected(selectedIndex)
        }
        Log.i("Sergio", "Valor de selectedIndex: ${_selectedIndex.value}")
        if (currentSelectedIndex >= 0) {
            onItemDeselected(currentSelectedIndex)
            if (currentSelectedIndex == selectedIndex || !newDataTapped) {
                Log.i("Sergio", "entra donde no deberia")
                _selectedIndex.value = -1
            }
        }
    }

    /**
     * Calculate the gap width between the arcs based on [gapPercentage]. The percentage is applied
     * to the average count to determine the width in pixels.
     */
    private fun calculateGap(gapPercentage: Float): Float {
        if (data.value!!.items.isEmpty()) return 0f

        return (data.value?.totalAmount!! / data.value?.items!!.size) * gapPercentage
    }

    /**
     * Returns the total data points including the individual gap widths indicated by the
     * [gapPercentage].
     */
    private fun getTotalAmountWithGapIncluded(gapPercentage: Float): Float {
        val gap = calculateGap(gapPercentage)
        return data.value!!.totalAmount + (data.value!!.items.size * gap)
    }

    /**
     * The touch point start from Canvas top left which ranges from (0,0) -> (canvas.width, canvas.height).
     * We need to normalize this point so that it's based on the canvas center instead.
     */
    private fun Offset.findNormalizedPointFromTouch(canvasCenter: Offset) =
        Offset(this.x, canvasCenter.y + (canvasCenter.y - this.y))

    /**
     * Calculate the touch angle based on the canvas center. Then adjust the angle so that
     * drawing starts from the 4th quadrant instead of the first.
     */
    private fun calculateTouchAngleAccordingToCanvas(
        canvasCenter: Offset,
        normalizedPoint: Offset
    ): Float {
        val angle = calculateTouchAngleInDegrees(canvasCenter, normalizedPoint)
        return adjustAngleToCanvas(angle).toFloat()
    }

    /**
     * Calculate touch angle in radian using atan2(). Afterwards, convert the radian to degrees to be
     * compared to other data points.
     */
    private fun calculateTouchAngleInDegrees(
        canvasCenter: Offset,
        normalizedPoint: Offset
    ): Double {
        val touchInRadian = kotlin.math.atan2(
            normalizedPoint.y - canvasCenter.y,
            normalizedPoint.x - canvasCenter.x
        )
        return touchInRadian * -180 / Math.PI // Convert radians to angle in degrees
    }

    /**
     * Start from 4th quadrant going to 1st quadrant, degrees ranging from 0 to 360
     */
    private fun adjustAngleToCanvas(angle: Double) = (angle + TOTAL_ANGLE) % TOTAL_ANGLE

    /**
     * Find the distance based on two points in a graph. Calculated using the pythagorean theorem.
     */
    private fun findTouchDistanceFromCenter(center: Offset, touch: Offset) =
        sqrt((touch.x - center.x).pow(2) + (touch.y - center.y).pow(2))
}

private data class DrawingAngles(val start: Float, val end: Float)

private fun DrawingAngles.isInsideAngle(angle: Float) =
    angle > this.start && angle < this.start + this.end