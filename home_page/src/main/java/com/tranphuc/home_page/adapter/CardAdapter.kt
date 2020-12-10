package com.tranphuc.home_page.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.get
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tranphuc.home_page.R
import com.tranphuc.home_page.model.ItemCard
import com.tranphuc.home_page.model.ItemPerson
import com.tranphuc.home_page.utils.DateUtils
import kotlinx.android.synthetic.main.item_card.view.*

class CardAdapter(var listItemCard: MutableList<ItemCard>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val TYPE_LOADING = 0
        val TYPE_ITEM_CARD = 1
    }

    override fun getItemViewType(position: Int): Int {
        return listItemCard.get(position).type
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_ITEM_CARD -> {
                var view =
                    LayoutInflater.from(viewGroup.context)
                        .inflate(R.layout.item_card, viewGroup, false)
                return CardViewHolder(view)
            }
            else -> {
                var view =
                    LayoutInflater.from(viewGroup.context)
                        .inflate(R.layout.item_loading, viewGroup, false)
                return LoadingViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            TYPE_ITEM_CARD -> {
                (viewHolder as? CardViewHolder)?.bindData(listItemCard.get(position).itemPerson)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItemCard.size
    }


    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // detech previous clicking icon.
        private var mPositionOfLastIconClick = 0

        fun bindData(itemPerson: ItemPerson) {
            mPositionOfLastIconClick = 0
            // bind avatar
            Glide.with(itemView.context).load(itemPerson.avatar).into(itemView.ivAvatar)

            // bind text
            bindText(0, itemPerson)

            // bind list icon
            bindListIcon(0, itemPerson)
        }

        private fun bindText(positionActiveIcon: Int, itemPerson: ItemPerson) {
            when (positionActiveIcon) {
                0 -> {
                    itemView.tvFirstContent.text =
                        itemView.context.resources.getString(R.string.my_name_is)
                    itemView.tvSecondContent.text = itemPerson.name
                }
                1 -> {
                    itemView.tvFirstContent.text =
                        itemView.context.resources.getString(R.string.my_birthday_is)
                    itemView.tvSecondContent.text = DateUtils.formatLongToDate(itemPerson.birthDay * 1000)
                }
                2 -> {
                    itemView.tvFirstContent.text =
                        itemView.context.resources.getString(R.string.my_address_is)
                    itemView.tvSecondContent.text = itemPerson.address
                }
                3 -> {
                    itemView.tvFirstContent.text =
                        itemView.context.resources.getString(R.string.my_phone_is)
                    itemView.tvSecondContent.text = itemPerson.phone
                }
            }
        }

        private fun bindListIcon(positionActiveIcon: Int, itemPerson: ItemPerson) {
            itemView.lnListIcon.removeAllViews()
            itemPerson.listIcon.forEachIndexed { index, itemIcon ->
                var ivIcon = LayoutInflater.from(itemView.context)
                    .inflate(R.layout.item_icon, itemView.lnListIcon, false) as ImageView

                if (positionActiveIcon == index) {
                    Glide.with(itemView.context).load(itemIcon.iconActive).into(ivIcon)
                } else {
                    Glide.with(itemView.context).load(itemIcon.iconNotActive).into(ivIcon)
                }
                itemView.lnListIcon.addView(ivIcon)

                // event
                ivIcon.setOnClickListener {
                    if (index != mPositionOfLastIconClick) {
                        // rebind text
                        bindText(index,itemPerson)

                        // load icon not active for before icon
                        Glide.with(itemView.context)
                            .load(itemPerson.listIcon.get(mPositionOfLastIconClick).iconNotActive)
                            .into(itemView.lnListIcon.get(mPositionOfLastIconClick) as ImageView)

                        // load icon active for current icon
                        Glide.with(itemView.context).load(itemIcon.iconActive).into(ivIcon)

                        // update mPositionOfLastIconClick
                        mPositionOfLastIconClick = index
                    }
                }
            }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun updateList(listItemCardNew: List<ItemCard>) {
        val diffCallback = CardDiffCallBack(listItemCard, listItemCardNew)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listItemCard.clear()
        listItemCard.addAll(listItemCardNew)
        diffResult.dispatchUpdatesTo(this)
    }
}

