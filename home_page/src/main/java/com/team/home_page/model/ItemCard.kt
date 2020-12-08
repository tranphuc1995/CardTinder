package com.team.home_page.model

import com.team.home_page.adapter.CardAdapter

data class ItemCard(
    var type: Int = CardAdapter.TYPE_LOADING,
    var itemPerson: ItemPerson = ItemPerson(),
    var positionIconChoose: Int = 0
) {
}

