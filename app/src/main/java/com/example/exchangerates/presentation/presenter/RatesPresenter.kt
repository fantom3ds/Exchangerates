package com.example.exchangerates.presentation.presenter

import com.example.exchangerates.data.repository.LoginRepository
import com.example.exchangerates.data.repository.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RatesPresenter(val view: IRatesView) {

    val repository = MainRepository.instance
    val loginRepository = LoginRepository.instance

    fun showRates(baseRate: String) {
        view.setLoading(true)
        repository.getRates(baseRate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.setLoading(false)
                val S = it.toString().replace(Regex("[,()]")) {
                    when (it.value) {
                        "(" -> "\n"
                        ")" -> "\n"
                        "," -> "\n"
                        else -> it.value
                    }
                }
                val S2 = it.rates.toString().replace(Regex("[,()]")) {
                    when (it.value) {
                        "(" -> "\n"
                        ")" -> "\n"
                        "," -> "\n"
                        else -> it.value
                    }
                }
                view.showRates(S + "\n\n" + S2.replace("Rates", ""))
            }, {
                view.setLoading(false)
                view.showError(it.message ?: "Unknown error")
            })
    }

    fun logout() {
        loginRepository.logout()
        view.logout()
    }

}