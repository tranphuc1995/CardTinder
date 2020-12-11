package com.tranphuc.home_page.adapter

import androidx.recyclerview.widget.DiffUtil
import com.tranphuc.home_page.model.ItemCard
import androidx.annotation.Nullable

class CardDiffCallBack(
    private val oldList: List<ItemCard>,
    private val newList: List<ItemCard>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return /*oldList[oldItemPosition].type == newList.get(newItemPosition).type*/ true
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition].itemPerson.id == newList[newPosition].itemPerson.id
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}