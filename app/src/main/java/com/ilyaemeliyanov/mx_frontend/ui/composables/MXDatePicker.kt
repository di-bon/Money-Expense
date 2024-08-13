package com.ilyaemeliyanov.mx_frontend.ui.composables

import android.provider.CalendarContract
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ilyaemeliyanov.mx_frontend.ui.theme.MXShapes
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MXDatePicker(date: String, onCalendarSelect: (String) -> Unit) {
    val calendarState = rememberSheetState()

    // UI Components
    CalendarDialog(state = calendarState, config = CalendarConfig(monthSelection = true, yearSelection = true), selection = CalendarSelection.Date { onCalendarSelect("${it.year}-${it.monthValue}-${it.dayOfMonth}")})
    Button(onClick = {calendarState.show()}, shape = MXShapes.medium, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.White), modifier = Modifier.fillMaxWidth()) {
        Text(date)
    }
}