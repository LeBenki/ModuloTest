package com.test.modulotech.api

import com.test.modulotech.model.ApiResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ModuloTechApi {

    @GET(API)
    fun getAllData(): Single<ApiResponse>


    companion object {

        const val BASE_URL = "http://storage42.com/"
        const val API = "modulotest/data.json"

        private var api: ModuloTechApi? = null

        operator fun invoke(): ModuloTechApi {

            if (api == null) {
                api = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build()
                    .create(ModuloTechApi::class.java)
            }
            return api!!
        }
    }
}