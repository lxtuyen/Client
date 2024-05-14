package com.example.heartsteel.domain.reponsitory

import com.example.heartsteel.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRespository  {
    fun loginUser(email: String ,password:String): Flow<Resource<AuthResult>>
    fun registerUser(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>>
}