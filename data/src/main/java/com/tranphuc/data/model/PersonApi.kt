package com.tranphuc.data.model

import com.google.gson.annotations.SerializedName

class PersonApi {
    @SerializedName("name")
    val name: Name? = Name()

    @SerializedName("location")
    val location: Location? = Location()

    @SerializedName("md5")
    val md5: String? = ""

    @SerializedName("dob")
    val dob: Long? = 0L

    @SerializedName("phone")
    val phone: String? = ""

    @SerializedName("picture")
    val picture: String? = ""
}

class Name {
    @SerializedName("first")
    val first: String? = ""

    @SerializedName("last")
    val last: String? = ""
}

class Location {
    @SerializedName("street")
    val street: String? = ""

    @SerializedName("city")
    val city: String? = ""
}