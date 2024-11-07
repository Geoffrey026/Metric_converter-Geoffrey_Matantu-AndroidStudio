package com.example.metric_converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.metric_converter.ui.theme.Metric_converterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Metric_converterTheme {
                MetricConverterApp()
            }
        }
    }
}

@Composable
fun MetricConverterApp() {
    var inputValue by remember { mutableStateOf(TextFieldValue("")) }
    var fromUnit by remember { mutableStateOf("") }
    var toUnit by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    val lengthUnits = listOf("Millimeter", "Centimeter", "Meter", "Kilometer")
    val weightUnits = listOf("Milligram", "Gram", "Kilogram", "Ton")
    val timeUnits = listOf("Second", "Minute", "Hour", "Day")

    // Select the appropriate units based on the selected category
    val units = when (selectedCategory) {
        "Weight" -> weightUnits
        "Time" -> timeUnits
        else -> lengthUnits
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Dropdown to select category (Length, Weight, or Time)
        DropdownMenu(selectedUnit = selectedCategory, units = listOf("Length", "Weight", "Time")) { category ->
            selectedCategory = category
            // Reset units and input value when category changes
            fromUnit = ""
            toUnit = ""
            inputValue = TextFieldValue("")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // TextField for value, enabled only if a category is selected
        TextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text("Enter value") },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedCategory.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // From Unit Dropdown, enabled only if a category is selected
        DropdownMenu(selectedUnit = fromUnit, units = if (selectedCategory.isNotEmpty()) units else listOf()) { fromUnit = it }
        Spacer(modifier = Modifier.height(8.dp))

        // To Unit Dropdown, enabled only if a category is selected
        DropdownMenu(selectedUnit = toUnit, units = if (selectedCategory.isNotEmpty()) units else listOf()) { toUnit = it }

        Spacer(modifier = Modifier.height(16.dp))

        // Convert Button, enabled only if category, fromUnit, toUnit, and input value are valid
        Button(
            onClick = {
                val value = inputValue.text.toDoubleOrNull()
                if (value != null && fromUnit.isNotEmpty() && toUnit.isNotEmpty()) {
                    result = when (selectedCategory) {
                        "Weight" -> ConClass.convertWeight(value, fromUnit, toUnit).toString()
                        "Time" -> ConClass.convertTime(value, fromUnit, toUnit).toString()
                        else -> ConClass.convertLength(value, fromUnit, toUnit).toString()
                    }
                } else {
                    result = "Invalid input"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedCategory.isNotEmpty() && fromUnit.isNotEmpty() && toUnit.isNotEmpty() && inputValue.text.isNotEmpty()
        ) {
            Text("Convert")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Result: $result $toUnit")
    }
}

@Composable
fun DropdownMenu(selectedUnit: String, units: List<String>, onUnitSelected: (String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Box {
        TextButton(
            onClick = { isExpanded = true },
            modifier = Modifier
                .clickable { isExpanded = true }
        ) {
            Text(if (selectedUnit.isNotEmpty()) selectedUnit else "Select")
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {
            units.forEach { unit ->
                DropdownMenuItem(
                    onClick = { onUnitSelected(unit); isExpanded = false },
                    text = { Text(unit) }
                )
            }
        }
    }
}
