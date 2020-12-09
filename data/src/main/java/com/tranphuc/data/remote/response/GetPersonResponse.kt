package com.tranphuc.data.remote.response

import com.google.gson.annotations.SerializedName
import com.tranphuc.data.model.PersonApi

class GetPersonResponse {
    @SerializedName("results")
    val listResult: MutableList<Result>? = ArrayList()
}

class Result {
    @SerializedName("user")
    val personApi: PersonApi? = PersonApi()
}


