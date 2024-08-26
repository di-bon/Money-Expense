package com.ilyaemeliyanov.mx_frontend.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
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
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXColors
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
    mxViewModel: MXViewModel,
    transactionList: List<Transaction>
) {
    if (transactionList.size > 1) {
        val minAmountTransaction = mxViewModel.transactions.sortedBy { it.amount }[0]
        val dataPoints = transactionList.sortedBy { it.date }.map {t ->
            val localDate = t.date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            val x = localDate.dayOfMonth.toFloat()
            Log.d("MXChart", x.toString())
            val y = t.amount // TODO: map this value to Y axis
            Point(x, y)
        }

        val steps = 10
        // Date
        val xAxisData = AxisData.Builder()
            .axisStepSize(40.dp)
            .backgroundColor(Color.Transparent)
            .steps(steps-1)
            .labelData { i -> i.toString() }
            .startPadding(30.dp)
            .labelAndAxisLinePadding(20.dp)
            .axisLineColor(Color.Black)
            .axisLabelColor(Color.DarkGray)
            .axisLabelDescription { "Date" }
            .build()


        // Transaction amount
        val yAxisData = AxisData.Builder()
            .axisStepSize(20.dp)
            .backgroundColor(Color.Transparent)
            .steps(dataPoints.size - 1)
            .labelData { i -> i.toString() }
            .startPadding(10.dp)
            .labelAndAxisLinePadding(20.dp)
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
                            color = MXColors.Default.ActiveColor,
                            lineType = LineType.SmoothCurve(isDotted = false)
                        ),
                        IntersectionPoint(color = MXColors.Default.PrimaryColor),
                        SelectionHighlightPoint(color = Color.Magenta),
                        ShadowUnderLine(
                            alpha = 0.5f,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MXColors.Default.ActiveColor,
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

        Box {
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                lineChartData = lineChartData
            )

        }
    }
    else {
        Text("No data to display")
    }



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