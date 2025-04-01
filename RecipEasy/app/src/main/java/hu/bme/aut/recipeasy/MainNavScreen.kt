package hu.bme.aut.recipeasy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.recipeasy.domain.model.main.MainViewModel
import hu.bme.aut.recipeasy.feature.recipe.read_recipe.ReadRecipeEvent
import hu.bme.aut.recipeasy.feature.recipe.read_recipe.ReadRecipeViewModel
import hu.bme.aut.recipeasy.navigation.NavGraph
import hu.bme.aut.recipeasy.navigation.Screen
import hu.bme.aut.recipeasy.ui.theme.appBackgroundColor
import hu.bme.aut.recipeasy.ui.theme.appPrimaryColor
import hu.bme.aut.recipeasy.ui.theme.onAppPrimaryColor
import hu.bme.aut.recipeasy.ui.theme.onTopAppBarColor
import hu.bme.aut.recipeasy.ui.theme.topAppBarColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavScreen(
    mainViewModel: MainViewModel,
    readRecipeViewModel: ReadRecipeViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val title by mainViewModel.title.observeAsState("")
    val fabIcon by mainViewModel.fabIcon.observeAsState(null)
    val fabAction by mainViewModel.fabAction.observeAsState { }
    val navigationLambda by mainViewModel.navigate.observeAsState()
    val showBackArrow by mainViewModel.showBackArrow.observeAsState(false)
    val showMenu by mainViewModel.showMenu.observeAsState(false)
    val snackBarHostState = SnackbarHostState()
    var drawerGesturesEnabled by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) {
        drawerGesturesEnabled = (it == DrawerValue.Open)
        return@rememberDrawerState true
    }
    val isFavourite by readRecipeViewModel.isFavourite.collectAsStateWithLifecycle(false)

    val navigateTo: (route: String) -> Unit = {
        scope.launch {
            navController.navigate(it)
            drawerState.close()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerGesturesEnabled,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                    .background(color = appPrimaryColor)
                    .padding(top = 50.dp, start = 20.dp, end = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 16.sp,
                    color = onAppPrimaryColor
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = stringResource(R.string.functions),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = onAppPrimaryColor
                )
                Spacer(modifier = Modifier.padding(10.dp))
                AppNavigationDrawerItem(
                    label = stringResource(R.string.recipes),
                    selectedRoute = mainViewModel.selectedMenuItem.value,
                    route = Screen.ListRecipes.route,
                    onClick = navigateTo
                )
                AppNavigationDrawerItem(
                    label = stringResource(R.string.categories),
                    selectedRoute = mainViewModel.selectedMenuItem.value,
                    route = Screen.ListCategories.route,
                    onClick = navigateTo
                )
                AppNavigationDrawerItem(
                    label = stringResource(R.string.about),
                    selectedRoute = mainViewModel.selectedMenuItem.value,
                    route = Screen.About.route,
                    onClick = navigateTo
                )
                HorizontalDivider(thickness = 2.dp, color = onAppPrimaryColor)
            }
        }) {
        Scaffold(
            containerColor = appBackgroundColor,
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            topBar = {
                if (title != "") {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = topAppBarColor,
                            titleContentColor = onTopAppBarColor,
                            navigationIconContentColor = onTopAppBarColor,
                            actionIconContentColor = onTopAppBarColor
                        ),
                        navigationIcon = {
                            if (showBackArrow)
                                IconButton(
                                    onClick = {
                                        navigationLambda?.invoke()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = Icons.AutoMirrored.Filled.ArrowBack.name
                                    )
                                }
                            else if (showMenu)
                                IconButton(
                                    onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open()
                                        else drawerState.close()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Sharp.Menu,
                                        contentDescription = Icons.Sharp.Menu.name,
                                    )
                                }
                        },
                        title = {
                            Text(
                                text = title,
                                fontSize = 30.sp
                            )
                        },
                        actions = {
                            if (title == stringResource(R.string.details)) {
                                IconButton(
                                    onClick = { readRecipeViewModel.onEvent(ReadRecipeEvent.ChangeRecipeFavouriteStatus) }
                                ) {
                                    Icon(
                                        imageVector = if (isFavourite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = (if (isFavourite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder).name
                                    )
                                }
                            }
                        }
                    )
                }
            },
            floatingActionButton = {
                fabIcon?.let {
                    FloatingActionButton(fabAction) {
                        Icon(
                            imageVector = fabIcon!!,
                            contentDescription = fabIcon!!.name
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->


            NavGraph(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                snackBarHostState = snackBarHostState,
                mainViewModel = mainViewModel,
                readRecipeViewModel = readRecipeViewModel
            )
        }
    }
}

@Composable
fun AppNavigationDrawerItem(
    label: String,
    selectedRoute: String?,
    route: String,
    onClick: (route: String) -> Unit
) {
    NavigationDrawerItem(
        modifier = Modifier.padding(bottom = 5.dp),
        label = {
            Text(
                label,
                fontSize = 14.sp,
                color = onAppPrimaryColor
            )
        },
        selected = selectedRoute == route,
        onClick = {
            if (route != selectedRoute) onClick(route)
        },
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = topAppBarColor,
        )
    )
}