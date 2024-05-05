package com.example.heartsteel.navigation

interface Router {
    fun goAddPodcasts()
    fun goAddPersons()
    fun goNotification()
    fun goSettings()
    fun goHistory()
    fun goProfile()
    fun goDetails(arg: Any?)
    fun goPlayerFull(arg: Any?)
    fun goSplash()
    fun goHome()
    fun goBack()
    fun goLogin()
    fun goSignup()
    fun <T : Any> getArgs(tag: String): T?
}