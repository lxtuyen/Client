package com.example.heartsteel.navigation

interface Router {
    fun goAddPersons()
    fun goNotification()
    fun goSettings()
    fun goSearchTag()
    fun goProfile()
    fun goDetails(arg: Any?)
    fun goPlayerFull(arg: Any?)
    fun goSplash()
    fun goHome()
    fun goBack()
    fun goLogin()
    fun goSignup()
    fun goLib()
}