package com.example.exchangerates.presentation.presenter

interface ILoginView {

    fun successLogin()

    fun setErrorCode(isError: Boolean, type: Int)

}