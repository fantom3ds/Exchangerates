package com.example.exchangerates.presentation.presenter

import com.example.exchangerates.data.repository.LoginRepository
import com.example.exchangerates.isEmail
import com.example.exchangerates.isPassword

class LoginPresenter(private val view: ILoginView) : ILoginPresenter {

    private val repository = LoginRepository.instance

    override fun checkEmail(email: String): Boolean {
        email.isEmail().apply {
            view.setErrorCode(!this, 1)
            return this
        }
    }

    override fun checkPassword(password: String): Boolean {
        password.isPassword().apply {
            if (password.length < 6) {
                view.setErrorCode(true, 21)
            } else {
                //если пароль неверен, то ошибка. И наоборот
                view.setErrorCode(!this, 2)
            }
            return this
        }
    }

    override fun comparePasswords(password: String, password2: String): Boolean {
        (password == password2).apply {
            view.setErrorCode(!this, 22)
            return this
        }
    }

    override fun checkNickname(nickname: String): Boolean {
        nickname.isEmpty().apply {
            view.setErrorCode(this, 3)
            return !this
        }
    }

    override fun login(email: String, password: String, isNew: Boolean) {
        if (email.isEmail() && password.isPassword()) {
            repository.login(email, password, isNew)
            view.successLogin()
        } else {
            view.setErrorCode(!email.isEmail(), 1)
            view.setErrorCode(!password.isPassword(), 2)
        }
    }

    override fun register(email: String, password: String, nickname: String) {
        if (email.isEmail() && password.isPassword() && nickname.isNotEmpty()) {
            repository.register(email, password, nickname)
            view.successLogin()
        } else {
            view.setErrorCode(!email.isEmail(), 1)
            view.setErrorCode(!password.isPassword(), 2)
            view.setErrorCode(nickname.isEmpty(), 3)
        }
    }

    override fun userExists(email: String): Boolean {
        repository.userExists(email).apply {
            view.setErrorCode(!this, 0)
            return this
        }
    }

}