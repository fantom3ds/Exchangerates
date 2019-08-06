package com.example.exchangerates

import java.util.regex.Pattern

fun String.isPassword():Boolean{
    return Pattern
        .compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{6,}\$")
        .matcher(this)
        .matches()
}

fun String.isEmail(): Boolean {
    return Pattern
        .compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
        .matcher(this)
        .matches()
}