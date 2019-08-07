package com.example.exchangerates.presentation.presenter

import com.example.exchangerates.data.model.RootObject

interface IRatesView {

    fun showRates(rates: String)

    fun setLoading(isLoading: Boolean)

    fun showError(message:String)

    fun logout()
}