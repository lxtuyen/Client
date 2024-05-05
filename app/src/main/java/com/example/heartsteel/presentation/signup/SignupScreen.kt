package com.example.heartsteel.presentation.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.heartsteel.components.LoginTextField
import com.example.heartsteel.components.TextTitle
import com.example.heartsteel.navigation.Router
import com.example.heartsteel.presentation.login.SignInViewModel
import com.example.heartsteel.presentation.login.defaultPadding
import com.example.heartsteel.presentation.login.itemSpacing
import kotlinx.coroutines.launch
@Composable
fun SignupScreen(router: Router? = null, viewModel: SignUpViewModel = hiltViewModel()) {
    val (email, setEmail) = rememberSaveable {
        mutableStateOf("")
    }
    val (username, setUsername) = rememberSaveable {
        mutableStateOf("")
    }
    val (password, setPassword) = rememberSaveable {
        mutableStateOf("")
    }
    val (Cpassword, setCPassword) = rememberSaveable {
        mutableStateOf("")
    }
    val isFieldsEmpty = email.isNotEmpty() && password.isNotEmpty()

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)

    val onSignInClick: () -> Unit = {
        router?.goLogin()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextTitle(
            text = "Đăng Ký",
            modifier = Modifier.padding(vertical = defaultPadding)
                .align(alignment = Alignment.Start)
        )
        LoginTextField(
            value = username,
            onValueChange = setUsername,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = Icons.Default.Person,
            labelText = "Username",
        )
        Spacer(Modifier.height(itemSpacing))
        LoginTextField(
            value = email,
            onValueChange = setEmail,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = Icons.Default.Person,
            labelText = "Gmail",
        )
        Spacer(Modifier.height(itemSpacing))
        LoginTextField(
            value = password,
            onValueChange = setPassword,
            modifier = Modifier.fillMaxWidth(),
            labelText = "Password",
            leadingIcon = Icons.Default.Lock,
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(itemSpacing))
        LoginTextField(
            value = Cpassword,
            onValueChange = setCPassword,
            modifier = Modifier.fillMaxWidth(),
            labelText = "Confirm Password",
            leadingIcon = Icons.Default.Lock,
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(itemSpacing))
        Button(
            onClick =  {
                scope.launch {
                    viewModel.registerUser(username,email)
                    router?.goLogin()
                    Toast.makeText(context,"Success Signup",Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFieldsEmpty,
        ) {
            Text("Đăng ký",color = Color.Black)
        }
        Spacer(Modifier.height(itemSpacing))
        AlternativeLoginOptions(
            onSignInClick = { onSignInClick() },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.TopCenter)
        )
        LaunchedEffect(key1 = state.value?.isSuccess )
        {
            scope.launch {
                if(state.value?.isSuccess?.isNotEmpty()==true)
                {
                    val success = state.value?.isSuccess
                    Toast.makeText(context,"$success",Toast.LENGTH_SHORT).show()
                    router?.goLogin()
                }
            }
        }
        LaunchedEffect(key1 = state.value?.isError )
        {
            scope.launch {
                if(state.value?.isError?.isNotEmpty()==true)
                {
                    val error = state.value?.isError
                    Toast.makeText(context,"$error",Toast.LENGTH_SHORT).show()
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (state.value?.isLoading == true) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun AlternativeLoginOptions(
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Already have an account", color = Color.White)
        Spacer(Modifier.height(itemSpacing))
        TextButton(onClick = onSignInClick) {
            Text("Sign Ip")
        }
    }
}

@Preview
@Composable
fun PrevLoginScreen() {
    SignupScreen()
}
