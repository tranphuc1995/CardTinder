package com.tranphuc.data.remote.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.tranphuc.data.remote.response.GetPersonResponse
import retrofit2.http.GET

interface PersonService {
    @GET("api/0.4/?randomapi")
    suspend fun getPerson(): NetworkResponse<GetPersonResponse, Unit>
}