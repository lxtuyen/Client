package com.example.heartsteel.viewModels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heartsteel.domain.model.Music
import com.example.heartsteel.domain.model.User
import com.example.heartsteel.repository.AuthResponsitory
import com.example.heartsteel.util.Resource
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthResponsitory
) : ViewModel() {
    val _signupstate = Channel<SignInState>()
    val signUpState = _signupstate.receiveAsFlow()

    fun registerUser(
        email: String,
        password: String,
        id: String,
        albums: List<Music> = emptyList(),
        history: List<Music> = emptyList()
    ) = viewModelScope.launch {
        repository.registerUser(email, password, id, albums, history).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val userId = Firebase.auth.currentUser?.uid
                    userId?.let { uid ->
                        val user = User(
                            email = email,
                            password = password,
                            id = id,
                            albums = albums,
                            history = history
                        )
                        val database = FirebaseDatabase.getInstance().reference.child("users").child(uid)
                        database.setValue(user)
                    }
                }
                is Resource.Loading -> {
                    _signupstate.send(SignInState(isLoading = true))
                }

                is Resource.Error -> {
                    _signupstate.send(SignInState(isError = "Sign Up Error"))
                }
            }
        }
    }
}