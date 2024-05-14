package com.example.heartsteel.presentation.login

import android.util.Patterns
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
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import com.example.heartsteel.components.LoginTextField
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.heartsteel.components.TextTitle
import com.example.heartsteel.navigation.Router
import kotlinx.coroutines.launch

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun LoginScreen(router: Router? = null, viewModel: SignInViewModel = hiltViewModel()) {

    val (email, setEmail) = rememberSaveable {
        mutableStateOf("")
    }
    val (password, setPassword) = rememberSaveable {
        mutableStateOf("")
    }
    val isFieldsEmpty = password.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signInState.collectAsState(initial = null)

    val onSignUpClick: () -> Unit = {
        router?.goSignup()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextTitle(
            text = "Đăng Nhập",
            modifier = Modifier
                .padding(vertical = defaultPadding)
                .align(alignment = Alignment.Start)
        )
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
        Button(
            onClick = {
                scope.launch {
                    viewModel.loginUser(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFieldsEmpty,
        ) {
            Text("Đăng nhập", color = Color.Black)
        }
        Spacer(Modifier.height(itemSpacing))
        AlternativeLoginOptions(
            onSignUpClick = { onSignUpClick()},
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.TopCenter)
        )
        LaunchedEffect(key1 = state.value?.isSuccess)
        {
            scope.launch {
                if (state.value?.isSuccess?.isNotEmpty() == true) {
                    Toast.makeText(context, "Success Login", Toast.LENGTH_SHORT).show()
                    router?.goHome()
                }
            }
        }
        LaunchedEffect(key1 = state.value?.isError)
        {
            scope.launch {
                if (state.value?.isError?.isNotEmpty() == true) {
                    val error = state.value?.isError
                    Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun AlternativeLoginOptions(
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Don't have an Account?", color = Color.White)
        Spacer(Modifier.height(itemSpacing))
        TextButton(onClick = onSignUpClick) {
            Text("Sign Up")
        }
    }
}


@Preview
@Composable
fun PrevLoginScreen() {
    LoginScreen()
}