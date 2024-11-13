/*package com.example.recipeapp_projectfinal.firebase

import com.example.recipeapp_projectfinal.db.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    // Función para agregar un usuario
    suspend fun addUser(user: User) {
        usersCollection.document(user.id.toString()).set(user).await()
    }

    // Función para obtener un usuario por su correo
    suspend fun getUserByEmail(email: String): User? {
        val querySnapshot = usersCollection.whereEqualTo("email", email).get().await()
        return if (querySnapshot.documents.isNotEmpty()) {
            querySnapshot.documents[0].toObject(User::class.java)
        } else {
            null
        }
    }
}*/