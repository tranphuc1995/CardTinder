package com.team.home_page.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team.home_page.R
import com.team.home_page.model.ItemIcon
import kotlinx.android.synthetic.main.item_icon.view.*

class IconAdapter(
    var listItemIcon: MutableList<ItemIcon>,
    var iconBottomClick: CardAdapter.IconBottomClick,
    var positionCard: Int
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_icon, viewGroup, false)
        return IconViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        (viewHolder as IconViewHolder).bindData(
            listItemIcon.get(position),
            iconBottomClick,
            positionCard
        )


    }

    override fun getItemCount(): Int {
        return listItemIcon.size
    }


    class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(
            itemIcon: ItemIcon,
            iconBottomClick: CardAdapter.IconBottomClick,
            positionCard: Int
        ) {
            if (itemIcon.isChoose) {
                Glide.with(itemView.context).load(itemIcon.iconActive).into(itemView.ivIcon)
            } else {
                Glide.with(itemView.context).load(itemIcon.iconNotActive).into(itemView.ivIcon)
            }

            itemView.setOnClickListener {
                iconBottomClick.onItemBottomClick(positionCard, layoutPosition)
            }
        }
    }

    fun updateList(listItemIconNew: List<ItemIcon>) {
        val diffCallback = IconDiffCallBack(listItemIcon, listItemIconNew)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listItemIcon.clear()
        listItemIcon.addAll(listItemIconNew)
        diffResult.dispatchUpdatesTo(this)
    }
}