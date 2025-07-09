package com.dabi.nba_players_list.utils

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource


/**
 * A sealed class representing different types of UI text resources.
 * This class provides a unified way to handle string, plural, and array resources,
 * allowing for easy retrieval of the actual text in both Composable and non-Composable contexts.
 *
 * It helps in abstracting the resource handling logic, making it easier to manage
 * and use localized strings throughout the application.
 */
sealed class UiTexts{
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ): UiTexts()

    class PluralResource(
        @PluralsRes val resId: Int,
        val quantity: Int,
        vararg val args: Any
    ): UiTexts()

    class ArrayResource(
        @ArrayRes val resId: Int,
        val index: Int = 0
    ): UiTexts()


    @Composable
    fun asString(): String{
        return when(this){
            is StringResource -> stringResource(resId, *args)
            is PluralResource -> pluralStringResource(resId, quantity, *args)
            is ArrayResource -> stringArrayResource(resId)[index]
        }
    }
    fun asString(context: Context): String{
        return when(this){
            is StringResource -> context.getString(resId, *args)
            is PluralResource -> context.resources.getQuantityString(resId, quantity, *args)
            is ArrayResource -> context.resources.getStringArray(resId)[index]
        }
    }

    @Composable
    fun asArray(): List<String> {
        return when(this){
            is ArrayResource -> stringArrayResource(resId).toList()
            else -> {
                listOf()}
        }
    }
    fun asArray(context: Context): List<String> {
        return when(this){
            is ArrayResource -> context.resources.getStringArray(resId).toList()
            else -> {
                listOf()}
        }
    }
}
