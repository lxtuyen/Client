package com.example.heartsteel.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heartsteel.data.reponsitory.AuthRespository
import com.example.heartsteel.domain.model.User
import com.example.heartsteel.util.Resource
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRespository
) : ViewModel() {

    val _signUpState = Channel<SignUpState>()
    val signUpState = _signUpState.receiveAsFlow()

    fun registerUser(
        email: String,
        password: String,
        username: String,
    ) = viewModelScope.launch {
        repository.registerUser(email,password)
            .collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val userId = Firebase.auth.currentUser?.uid
                        userId?.let { uid ->
                            val user = User(
                                email = email,
                                id = uid,
                                username = username,
                            )
                            val database =
                                FirebaseDatabase.getInstance().reference.child("users").child(uid)
                            database.setValue(user)
                        }
                    }

                    is Resource.Loading -> {
                        _signUpState.send(SignUpState(isLoading = true))
                    }

                    is Resource.Error -> {

                        _signUpState.send(SignUpState(isError = result.message))
                    }
                }

            }
    }

}