package com.ilyaemeliyanov.mx_frontend.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXShapes
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MXDatePicker(date: String, onCalendarSelect: (String) -> Unit) {
    val calendarState = rememberSheetState()

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(monthSelection = true, yearSelection = true),
        selection = CalendarSelection.Date { onCalendarSelect("${it.year}-${it.monthValue}-${it.dayOfMonth}") })
    Button(
        onClick = { calendarState.show() },
        shape = MXShapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(date)
    }
}