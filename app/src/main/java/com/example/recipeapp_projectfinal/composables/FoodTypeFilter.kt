package com.example.recipeapp_projectfinal.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FoodTypeFilter(
    selectedFoodType: String,
    onFoodTypeSelected: (String) -> Unit
) {
    val foodTypes = listOf(
        "main course", "side dish", "dessert", "appetizer", "salad",
        "bread", "breakfast", "soup", "beverage", "sauce", "marinade",
        "fingerfood", "snack", "drink"
    )

    var expanded by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text("Tipo de comida: $selectedFoodType")

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = selectedFoodType,
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .padding(16.dp)
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    foodTypes.forEach { foodType ->
                        DropdownMenuItem(
                            text = { Text(foodType) },
                            onClick = {
                                onFoodTypeSelected(foodType)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}