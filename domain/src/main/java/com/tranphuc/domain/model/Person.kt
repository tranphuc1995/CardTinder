package com.tranphuc.domain.model

data class Person(
    var id: String = "",
    var name: String = "",
    var birthDay: Long = 0,
    var address: String = "",
    var phone: String = "",
    var avatar: String = ""
) {
}