package hu.bme.aut.recipeasy.navigation

sealed class Screen(val route: String) {
    data object ListRecipes: Screen("list-recipes")
    data object ReadRecipe: Screen("read-recipe/{id}") {
        fun passId(id: String) = "read-recipe/$id"
    }
    data object ListCategories: Screen("list-categories")
    data object ReadCategory: Screen("read-category/{id}") {
        fun passId(id: String) = "read-category/$id"
    }
    data object About: Screen("about")
}