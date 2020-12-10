package com.tranphuc.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class PersonEntity(
    @PrimaryKey
    var id: String = "",
    var avatar: String = "",
    var name: String = "",
    var location: String = "",
    var phone: String = "",
    var dob: Long = 0L
)  {
}