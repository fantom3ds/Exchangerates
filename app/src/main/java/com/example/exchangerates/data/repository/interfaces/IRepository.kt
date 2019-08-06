package com.example.exchangerates.data.repository.interfaces

import com.example.exchangerates.data.model.RootObject
import io.reactivex.Single

interface IRepository {

    fun getRates(baseRate: String): Single<RootObject>
}