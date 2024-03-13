package com.lpg.disney_api

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface ServicioApi {
    @GET("/character/")
    suspend fun conseguirPersonaje():Response<InfoWeb>
}