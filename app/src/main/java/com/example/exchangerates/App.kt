package com.example.exchangerates

import android.app.Application
import android.content.Context


class App : Application() {

    companion object {
        //статичная переменная
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        //в статичную переменную запишем себя
        instance = this
    }

    //Переопределяем геттеры и сеттеры
    var token: String?
        get() = getSharedPreferences("auth", Context.MODE_PRIVATE).getString("token", null)
        set(value) = getSharedPreferences("auth", Context.MODE_PRIVATE).edit().putString("token", value).apply()

    var userName: String?
        get() = getSharedPreferences("auth", Context.MODE_PRIVATE).getString("userName", null)
        set(value) = getSharedPreferences("auth", Context.MODE_PRIVATE).edit().putString("userName", value).apply()

    var userPassword: String?
        get() = getSharedPreferences("auth", Context.MODE_PRIVATE).getString("userPassword", null)
        set(value) = getSharedPreferences("auth", Context.MODE_PRIVATE).edit().putString("userPassword", value).apply()

    var userMail: String?
        get() = getSharedPreferences("auth", Context.MODE_PRIVATE).getString("userMail", null)
        set(value) = getSharedPreferences("auth", Context.MODE_PRIVATE).edit().putString("userMail", value).apply()

    fun logOut() {
        token = null
    }

    fun logIn(userName: String?, mail: String?, password: String?) {
        token = mail
        userName.let {
            this.userName = it
        }
        userPassword = password
        userMail = mail
    }
}