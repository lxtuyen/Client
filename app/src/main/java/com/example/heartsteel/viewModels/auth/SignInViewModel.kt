package com.example.heartsteel.viewModels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.example.heartsteel.repository.AuthResponsitory
import com.example.heartsteel.util.Resource
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository :AuthResponsitory
): ViewModel() {
    val _signinState = Channel<SignInState>()
    val signInState = _signinState.receiveAsFlow()

    fun loginUser(email:String, password:String) = viewModelScope.launch {
        repository.loginUser(email,password).collect{
                result->
            when(result)
            {
                is Resource.Success->{
                    _signinState.send(SignInState(isSuccess = "Sign In Success"))
                }
                is Resource.Loading->{
                    _signinState.send(SignInState(isLoading = true))
                }
                is Resource.Error->{
                    _signinState.send(SignInState(isError = "Sign In Error"))
                }

            }
        }
    }
}