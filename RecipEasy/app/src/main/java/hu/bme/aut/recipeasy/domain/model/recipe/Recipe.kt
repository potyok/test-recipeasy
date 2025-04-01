package hu.bme.aut.recipeasy.domain.model.recipe

data class Recipe(
    val idMeal: String,
    val strMeal: String,
    val strMealAlternate: String?,
    val strCategory: String,
    val strArea: String,
    val strInstructions: String,
    val strMealThumb: String,
    val strYoutube: String,
    val strTags: String?,
)

fun Recipe.videoId(): String {
    val regex = Regex("(?:v=|/)([0-9A-Za-z_-]{11})")
    return regex.find(strYoutube)?.groupValues?.get(1) ?: ""
}

fun Recipe.tags(): List<String> {
    return strTags?.split(",") ?: listOf()
}