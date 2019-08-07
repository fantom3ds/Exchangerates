package com.example.exchangerates.presentation.presenter

interface ILoginPresenter {

    fun checkEmail(email: String): Boolean

    fun checkPassword(password: String): Boolean

    fun comparePasswords(password: String, password2: String): Boolean

    fun checkNickname(nickname: String): Boolean

    fun login(email: String, password: String, isNew: Boolean)

    fun register(email: String, password: String, nickname: String)

    fun userExists(email: String): Boolean
}