import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp_projectfinal.db.NewRecipe
import com.example.recipeapp_projectfinal.db.NewRecipeDao
import kotlinx.coroutines.launch

class NewRecipeViewModel(private val dao: NewRecipeDao) : ViewModel() {

    // Listado de todas las recetas
    val allRecipes = mutableStateOf<List<NewRecipe>>(emptyList())

    // Función para agregar una receta
    fun addRecipe(recipe: NewRecipe) {
        viewModelScope.launch {
            dao.insertRecipe(recipe)
            loadRecipes()  
        }
    }

    // Función para cargar todas las recetas
    fun loadRecipes() {
        viewModelScope.launch {
            allRecipes.value = dao.getAllRecipes()
        }
    }

    // Función para eliminar todas las recetas
    fun deleteAllRecipes() {
        viewModelScope.launch {
            dao.deleteAllRecipes()
            loadRecipes()  // Actualiza la lista después de eliminar
        }
    }
}
