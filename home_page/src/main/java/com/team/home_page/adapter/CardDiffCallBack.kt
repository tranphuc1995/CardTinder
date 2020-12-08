package com.team.home_page.adapter

import androidx.recyclerview.widget.DiffUtil
import com.team.home_page.model.ItemCard
import androidx.annotation.Nullable

class CardDiffCallBack(
    private val oldList: List<ItemCard>,
    private val newList: List<ItemCard>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].type == newList.get(newItemPosition).type
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        when (oldList.get(oldPosition).type) {
            CardAdapter.TYPE_ITEM_CARD -> {
                val (_, _, positionIconChooseOld) = oldList[oldPosition]
                val (_, _, positionIconChooseNew) = newList[newPosition]
                return positionIconChooseOld == positionIconChooseNew
            }
            else -> {
                return true
            }
        }
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}