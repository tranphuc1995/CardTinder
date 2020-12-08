package com.team.home_page.adapter

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.team.home_page.model.ItemIcon

class IconDiffCallBack (
    private val oldList: List<ItemIcon>,
    private val newList: List<ItemIcon>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isChoose == newList.get(newItemPosition).isChoose
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList.get(oldPosition) == newList.get(newPosition)
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}