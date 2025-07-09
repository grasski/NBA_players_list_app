package com.dabi.nba_players_list.utils

import kotlin.math.roundToInt


/**
 * Converts a string representing feet and inches to centimeters.
 *
 * The input string should be in the format "feet-inches" (e.g., "6-2").
 *
 * @return The height in centimeters as a Float, rounded to two decimal places.
 * @throws NumberFormatException if the input string is not in the correct format or if the foot/inch values cannot be parsed to Float.
 * @throws IndexOutOfBoundsException if the input string does not contain a hyphen separator.
 */
fun String.footInchToCm(): Float{
    val foot = this.split("-")[0].toFloat()
    val inch = this.split("-")[1].toFloat()
    val cm = (foot * 30.48f) + (inch * 2.54f)
    return (cm * 100).roundToInt() / 100.0f
}

/**
 * Converts a weight from pounds to kilograms.
 *
 * @return The weight in kilograms as a Float, rounded to two decimal places.
 * @receiver String representing the weight in pounds.
 * @throws NumberFormatException if the string cannot be parsed into a Float.
 */
fun String.poundToKg(): Float{
    val kg = this.toFloat() / 2.205f
    return (kg * 100).roundToInt() / 100.0f
}