package hu.bme.aut.recipeasy.ui.model

import android.content.Context
import androidx.annotation.StringRes
import hu.bme.aut.recipeasy.R

sealed class UiText {
    data class DynamicString(val value: String): UiText()
    data class StringResource(@StringRes val id: Int): UiText()

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> this.value
            is StringResource -> context.getString(this.id)
        }
    }
}

fun Throwable.toUiText(): UiText {
    val message = this.message.orEmpty()
    return if (message.isBlank()) {
        UiText.StringResource(R.string.an_error_occurred)
    } else {
        UiText.DynamicString(message)
    }
}