package com.example.heartsteel.repository

import com.example.heartsteel.domain.model.Music
import com.example.heartsteel.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
interface AuthResponsitory  {
    fun loginUser(email: String ,password:String): Flow<Resource<AuthResult>>
    fun registerUser(
        email: String,
        password: String,
        id: String,
        albums: List<Music> = emptyList(),
        history: List<Music> = emptyList()
    ): Flow<Resource<AuthResult>>
}