package com.example.metric_converter

object ConClass {
    private val lengthUnits = mapOf(
        "Millimeter" to 0.001,
        "Centimeter" to 0.01,
        "Meter" to 1.0,
        "Kilometer" to 1000.0
    )

    private val weightUnits = mapOf(
        "Milligram" to 0.000001,
        "Gram" to 0.001,
        "Kilogram" to 1.0,
        "Ton" to 1000.0
    )

    private val timeUnits = mapOf(
        "Second" to 1.0,
        "Minute" to 60.0,
        "Hour" to 3600.0,
        "Day" to 86400.0
    )


    fun convertLength(value: Double, fromUnit: String, toUnit: String): Double {
        val fromFactor = lengthUnits[fromUnit] ?: 1.0
        val toFactor = lengthUnits[toUnit] ?: 1.0
        return value * fromFactor / toFactor
    }


    fun convertWeight(value: Double, fromUnit: String, toUnit: String): Double {
        val fromFactor = weightUnits[fromUnit] ?: 1.0
        val toFactor = weightUnits[toUnit] ?: 1.0
        return value * fromFactor / toFactor
    }


    fun convertTime(value: Double, fromUnit: String, toUnit: String): Double {
        val fromFactor = timeUnits[fromUnit] ?: 1.0
        val toFactor = timeUnits[toUnit] ?: 1.0
        return value * fromFactor / toFactor
    }
}
