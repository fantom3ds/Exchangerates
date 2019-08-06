package com.example.exchangerates.data.repository.interfaces

interface ILoginRepository {
    fun login(email: String, password: String)
}