package com.dabi.nba_players_list.utils

import kotlin.math.roundToInt


/**
 * Converts a string representing feet and inches to centimeters.
 *
 * The input string should be in the format "feet-inches" (e.g., "6-2").
 *
 * @return The height in centimeters as a Float, rounded to two decimal places. Returns 0f if the input string is invalid or causes an exception during conversion.
 */
fun String.footInchToCm(): Float{
    return try {
        val foot = this.split("-")[0].toFloat()
        val inch = this.split("-")[1].toFloat()
        val cm = (foot * 30.48f) + (inch * 2.54f)
        (cm * 100).roundToInt() / 100.0f
    } catch (e: Exception){
        0f
    }
}

/**
 * Converts a weight from pounds to kilograms.
 *
 * @receiver String representing the weight in pounds.
 * @return The weight in kilograms as a Float, rounded to two decimal places. Returns 0f if the input string cannot be parsed into a Float.
 */
fun String.poundToKg(): Float{
    return try {
        val kg = this.toFloat() / 2.205f
        return (kg * 100).roundToInt() / 100.0f
    } catch (e: Exception){
        0f
    }
}