package com.tranphuc.home_page.model

data class ItemPerson(
    var id: String = "",
    var name: String = "",
    var birthDay: String = "",
    var address: String = "",
    var phone: String = "",
    var avatar: String = "",
    var listIcon: MutableList<ItemIcon> = ArrayList()
) {
}