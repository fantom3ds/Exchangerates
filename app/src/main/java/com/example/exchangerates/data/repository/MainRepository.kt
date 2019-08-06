package com.example.exchangerates.data.repository

import com.example.exchangerates.data.model.RootObject
import com.example.exchangerates.data.network.APIClient
import com.example.exchangerates.data.repository.interfaces.IRepository
import io.reactivex.Single

class MainRepository : IRepository {

    companion object {
        val instance: MainRepository by lazy { Holder.INSTANCE }
    }

    private object Holder {
        val INSTANCE = MainRepository()
    }

    val apiService = APIClient.instance.apiService

    override fun getRates(baseRate: String): Single<RootObject> {
        return apiService.getRates(baseRate)
    }

}