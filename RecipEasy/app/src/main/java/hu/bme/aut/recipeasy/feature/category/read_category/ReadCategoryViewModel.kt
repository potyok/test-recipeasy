package hu.bme.aut.recipeasy.feature.category.read_category

import androidx.lifecycle.ViewModel
import hu.bme.aut.recipeasy.domain.model.category.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ReadCategoryViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ReadCategoryState())
    val state = _state.asStateFlow()

    fun onEvent(event: ReadCategoryEvent) {
        when (event) {
            is ReadCategoryEvent.LoadCategory -> {
                loadCategory(event.id)
            }
        }
    }

    private fun loadCategory(id: String) {
        _state.update {
            it.copy(
                category = Category(
                    idCategory = id,
                    strCategory = "Beef",
                    strCategoryThumb = "https://www.themealdb.com/images/category/beef.png",
                    strCategoryDescription = "Beef is the culinary name for meat from cattle, particularly skeletal muscle. Humans have been eating beef since prehistoric times.[1] Beef is a source of high-quality protein and essential nutrients.[2]"
                )
            )
        }
    }
}

data class ReadCategoryState(
    val category: Category? = null
)

sealed class ReadCategoryEvent {
    data class LoadCategory(val id: String) : ReadCategoryEvent()
}