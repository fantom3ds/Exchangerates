package com.example.exchangerates.data.repository

import com.example.exchangerates.App
import com.example.exchangerates.data.repository.interfaces.ILoginRepository


class LoginRepository : ILoginRepository {

    companion object {
        val instance: LoginRepository by lazy { Holder.INSTANCE }
    }

    private object Holder {
        val INSTANCE = LoginRepository()
    }

    override fun login(email: String, password: String, isNew: Boolean) {
        App.instance.apply {
            userMail = email
            userPassword = password
            token = userMail

            if (isNew) {
                userName = "User"
            }
        }
    }

    override fun logout() {
        App.instance.token = null
    }

    override fun register(email: String, password: String, nickname: String) {
        App.instance.apply {
            userName = nickname
            userMail = email
            userPassword = password
            token = userMail
        }
    }

    override fun userExists(email: String): Boolean {
        return email == App.instance.userMail
    }

}
