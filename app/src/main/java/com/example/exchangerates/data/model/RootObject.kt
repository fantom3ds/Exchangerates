package com.example.exchangerates.data.model

class RootObject(
    val base: String?,
    val date: String?,
    val rates: Rates?
){
    override fun toString(): String {
        return """Базовая валюта: $base,
            |Актуальная дата: $date""".trimMargin()
    }
}