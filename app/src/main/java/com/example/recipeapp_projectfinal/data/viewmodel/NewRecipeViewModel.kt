import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp_projectfinal.db.NewRecipe
import com.example.recipeapp_projectfinal.db.NewRecipeDao
import kotlinx.coroutines.launch

class NewRecipeViewModel(private val dao: NewRecipeDao) : ViewModel() {


    val allRecipes = mutableStateOf<List<NewRecipe>>(emptyList())


    fun addRecipe(recipe: NewRecipe) {
        viewModelScope.launch {
            dao.insertRecipe(recipe)
            loadRecipes()
        }
    }

    fun loadRecipes() {
        viewModelScope.launch {
            allRecipes.value = dao.getAllRecipes()
        }
    }


    fun deleteAllRecipes() {
        viewModelScope.launch {
            dao.deleteAllRecipes()
            loadRecipes()
        }
    }

    fun getUserName(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("user_name", "Usuario desconocido") ?: "Usuario desconocido"
    }
}
