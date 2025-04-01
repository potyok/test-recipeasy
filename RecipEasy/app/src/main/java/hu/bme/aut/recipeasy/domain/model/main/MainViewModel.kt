package hu.bme.aut.recipeasy.domain.model.main

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.recipeasy.navigation.Screen
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val startDestination = Screen.ListRecipes.route

    private val _title = MutableLiveData("")
    val title: LiveData<String> = _title

    private val _navigate = MutableLiveData<() -> Unit>()
    val navigate: LiveData<() -> Unit> = _navigate

    private val _showBackArrow = MutableLiveData(false)
    val showBackArrow: LiveData<Boolean> = _showBackArrow

    private val _showMenu = MutableLiveData(false)
    val showMenu: LiveData<Boolean> = _showMenu

    private val _fabIcon = MutableLiveData<ImageVector?>(null)
    val fabIcon: LiveData<ImageVector?> = _fabIcon

    private val _fabAction = MutableLiveData({})
    val fabAction: LiveData<() -> Unit> = _fabAction

    private val _selectedMenuItem = MutableLiveData<String?>(null)
    val selectedMenuItem: LiveData<String?> = _selectedMenuItem

    fun updateTitle(
        newTitle: String,
        showBackArrow: Boolean = false,
        showMenu: Boolean = false,
        fabIcon: ImageVector? = null,
        fabAction: () -> Unit = {}
    ) {
        _title.value = newTitle
        _showBackArrow.value = showBackArrow
        _showMenu.value = showMenu
        _fabIcon.value = fabIcon
        _fabAction.value = fabAction
    }

    fun setNavigationLambda(lambda: () -> Unit) {
        _navigate.value = lambda
    }

    fun setSelectedMenuItem(item: String?) {
        _selectedMenuItem.value = item
    }
}