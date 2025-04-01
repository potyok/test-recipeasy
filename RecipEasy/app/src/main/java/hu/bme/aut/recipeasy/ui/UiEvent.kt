package hu.bme.aut.recipeasy.ui

import hu.bme.aut.recipeasy.ui.model.UiText


sealed class UiEvent {
    data object Success: UiEvent()

    data class Failure(val message: UiText): UiEvent()
}