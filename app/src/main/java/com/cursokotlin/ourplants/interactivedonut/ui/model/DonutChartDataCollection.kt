package com.cursokotlin.ourplants.interactivedonut.ui.model

data class DonutChartDataCollection(
    var items: List<DonutChartData>
) {
    var totalAmount: Float = items.sumOf { it.amount.toDouble() }.toFloat()
        private set
}
