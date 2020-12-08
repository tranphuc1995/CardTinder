package com.team.home_page.model

data class ItemPerson(
    var id: String = "",
    var name: String = "",
    var birthDay: String = "",
    var address: String = "",
    var phone: String = "",
    var avatar: String = "https://www.quangbinh.gov.vn/3cms/scripts/fckeditor/web/upload/Image/BacHo/HinhAnh/BacHo_ChanDung_08.jpg",
    var listIcon: MutableList<ItemIcon> = ArrayList()
) {
}