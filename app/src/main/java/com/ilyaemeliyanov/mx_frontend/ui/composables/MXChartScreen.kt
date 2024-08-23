package com.ilyaemeliyanov.mx_frontend.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.ilyaemeliyanov.mx_frontend.utils.StringFormatter
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModel
import com.ilyaemeliyanov.mx_frontend.viewmodel.MXViewModelSingleton
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import kotlin.math.abs

@Composable
fun MXChartScreen(
    mxViewModel: MXViewModel
) {
//
//    fun getDaysBetween(startDate: LocalDate, endDate: LocalDate): Long {
//        return ChronoUnit.DAYS.between(startDate, endDate)
//    }

    val transactions = mxViewModel.transactions
    val minAmountTransaction = mxViewModel.transactions.sortedBy { it.amount }[0]
    val dataPoints = transactions.sortedBy { it.date }.map {t ->
        val localDate = t.date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        val x = localDate.dayOfMonth.toFloat()
        val y = t.amount + minAmountTransaction.amount // TODO: map this value to Y axis
        Point(x, y)
    }

    Log.d("MXChartScree", dataPoints.toString())

    // Date
    val xAxisData = AxisData.Builder()
        .axisStepSize(20.dp)
        .backgroundColor(Color.Transparent)
        .steps(dataPoints.size - 1)
        .labelData { i -> "${(i+1).toString()}/${LocalDate.now().monthValue}" }
        .labelAndAxisLinePadding(10.dp)
        .axisLineColor(Color.Black)
        .axisLabelColor(Color.DarkGray)
        .axisLabelDescription { "Date" }
        .build()


    // Transaction amount
    val yAxisData = AxisData.Builder()
        .axisStepSize(20.dp)
        .backgroundColor(Color.Transparent)
        .steps(dataPoints.size - 1)
        .labelData { i -> (transactions[i].amount).toString() }
        .labelAndAxisLinePadding(5.dp)
        .axisLineColor(Color.Black)
        .axisLabelColor(Color.DarkGray)
        .axisLabelDescription { "Transaction" }
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = dataPoints,
                    LineStyle(
                        color = MaterialTheme.colors.primary,
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(color = MaterialTheme.colors.primary),
                    SelectionHighlightPoint(color = MaterialTheme.colors.secondary),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colors.secondary,
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            )
        ),
        backgroundColor = MaterialTheme.colors.surface,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(color = Color.LightGray)
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )


}

//@Preview
//@Composable
//fun MXChartScreenPreview() {
//    val mxViewModel: MXViewModel = MXViewModelSingleton.getInstance()
//    MXAlertDialog(
//        title = "Transaction Chart",
//        dismissLabel = "Dismiss",
//        confirmLabel = "Done",
//        onDismiss = { /*TODO*/ },
//        onConfirm = { /*TODO*/ }) {
//       MXChartScreen(mxViewModel = mxViewModel)
//    }
//}