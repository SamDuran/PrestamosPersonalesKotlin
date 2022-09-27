package edu.ucne.prestamospersonales.util

import java.util.*


fun veryShortName(value: String): Boolean {
    return value.length < 2
}

fun phoneNumberNoValid(value: String): Boolean {
    for (caracter in value) if (!caracter.isDigit()) return false
    return value.length != 10
}

fun emailNoValid(email: String): Boolean {
    val regex = Regex("[A-Za-z0-9_]{3,}@[A-Za-z0-9]{3,}(\\.[A-Za-z0-9_]{3,})(\\.[A-Za-z0-9_]{2,})*")
    return !regex.matches(email)
}

@Suppress("DEPRECATION")
fun birthDateNoValid(fecha: Date): Boolean {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR) - 17
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    return fecha > Date(year, month, day) || fecha < Date(1930, 1, 1)
}