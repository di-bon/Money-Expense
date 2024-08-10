package com.ilyaemeliyanov.mx_frontend.viewmodel

object MXViewModelSingleton {
    private var instance: MXViewModel? = null

    fun getInstance(): MXViewModel {
        if (instance == null) {
           instance = MXViewModel(MXRepository())
        }
        return instance!!
    }
}