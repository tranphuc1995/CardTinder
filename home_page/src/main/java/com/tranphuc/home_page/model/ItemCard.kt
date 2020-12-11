package com.tranphuc.home_page.model

import com.tranphuc.home_page.adapter.CardAdapter

data class ItemCard(
    var type: Int = CardAdapter.TYPE_LOADING,
    var itemPerson: ItemPerson = ItemPerson()
)

