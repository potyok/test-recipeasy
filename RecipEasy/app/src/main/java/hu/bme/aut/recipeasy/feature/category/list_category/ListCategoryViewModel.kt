package hu.bme.aut.recipeasy.feature.category.list_category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.recipeasy.domain.model.category.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCategoryViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ListCategoryScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: ListCategoryScreenEvent) {
        when (event) {
            is ListCategoryScreenEvent.RefreshCategories -> {
                loadRecipes()
            }
        }
    }

    private fun loadRecipes() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            _state.update {
                it.copy(
                    isLoading = false,
                    categories = listOf(
                        Category(
                            idCategory = "1",
                            strCategory = "Beef",
                            strCategoryThumb = "https://www.themealdb.com/images/category/beef.png",
                            strCategoryDescription = "Beef is the culinary name for meat from cattle, particularly skeletal muscle. Humans have been eating beef since prehistoric times.[1] Beef is a source of high-quality protein and essential nutrients.[2]"
                        )
                    )
                )
            }
        }
    }
}

data class ListCategoryScreenState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
)

sealed class ListCategoryScreenEvent {
    data object RefreshCategories : ListCategoryScreenEvent()
}