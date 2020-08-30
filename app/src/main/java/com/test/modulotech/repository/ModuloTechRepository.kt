package com.test.modulotech.repository

import com.test.modulotech.api.ModuloTechApi
import com.test.modulotech.model.ApiResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ModuloTechRepository(api: ModuloTechApi) {

    val getAllData: Single<ApiResponse> = api.getAllData()
        .subscribeOn(Schedulers.io())
}