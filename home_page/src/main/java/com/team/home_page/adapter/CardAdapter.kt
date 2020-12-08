package com.team.home_page.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team.home_page.R
import com.team.home_page.model.ItemCard
import com.team.home_page.model.ItemIcon
import com.team.home_page.model.ItemPerson
import kotlinx.android.synthetic.main.item_card.view.*

class CardAdapter(var listItemCard: MutableList<ItemCard>,var  iconBottomClick: IconBottomClick) :
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
                (viewHolder as? CardViewHolder)?.bindData(
                    listItemCard.get(position).itemPerson,
                    listItemCard.get(position).positionIconChoose,
                    iconBottomClick
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return listItemCard.size
    }


    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var mIconAdapter: IconAdapter? = null

        fun bindData(
            itemPerson: ItemPerson,
            positionItemChoose: Int,
            iconBottomClick: IconBottomClick
        ) {

            // bind avatar
            Glide.with(itemView.context).load(itemPerson.avatar).into(itemView.ivAvatar)

            // bind text
            when (positionItemChoose) {
                0 -> {
                    itemView.tvFirstContent.text =
                        itemView.context.resources.getString(R.string.my_name_is)
                    itemView.tvSecondContent.text = itemPerson.name
                }
                1 -> {
                    itemView.tvFirstContent.text =
                        itemView.context.resources.getString(R.string.my_birthday_is)
                    itemView.tvSecondContent.text = itemPerson.birthDay
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

            // bind list icon
            initRvIcon(itemPerson.listIcon, iconBottomClick, layoutPosition)
        }

        private fun initRvIcon(
            listItemIcon: MutableList<ItemIcon>,
            iconBottomClick: IconBottomClick,
            positionOfCard: Int
        ) {
            var listItemIconTemp: MutableList<ItemIcon> = ArrayList()
            itemView.rvIcon.layoutManager = GridLayoutManager(itemView.context, listItemIcon.size)
            mIconAdapter = IconAdapter(listItemIconTemp, iconBottomClick, positionOfCard)
            itemView.rvIcon.adapter = mIconAdapter
            mIconAdapter?.updateList(listItemIcon)
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

    interface IconBottomClick {
        fun onItemBottomClick(positionOfCard: Int, positionIconInCard: Int)
    }
}

