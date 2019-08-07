package com.example.exchangerates.data.repository.interfaces

interface ILoginRepository {

    fun login(email: String, password: String, isNew: Boolean = true)

    fun logout()

    fun register(email: String, password: String, nickname: String)

    fun userExists(email: String): Boolean
}