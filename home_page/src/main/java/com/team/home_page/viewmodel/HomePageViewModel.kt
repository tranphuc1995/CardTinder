package com.team.home_page.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.team.home_page.R
import com.team.home_page.adapter.CardAdapter
import com.team.home_page.model.ItemCard
import com.team.home_page.model.ItemIcon

class HomePageViewModel : ViewModel() {
    private var mIsUserScrollLeft = false
    private var mCurrentRvPosition = 0
    private var mListItemCard: MutableList<ItemCard> = ArrayList()

    private var mLiveDataListItemCard: MutableLiveData<List<ItemCard>> = MutableLiveData()
    private var mLiveDataCircularScrollRv: MutableLiveData<Int> = MutableLiveData()

    fun getLiveDataListItemCard(): LiveData<List<ItemCard>> {
        return mLiveDataListItemCard
    }

    fun getLiveDataCircularScrollRv(): LiveData<Int> {
        return mLiveDataCircularScrollRv
    }

    fun getListItemCard() {
        var itemCard = ItemCard(CardAdapter.TYPE_ITEM_CARD)
        itemCard.itemPerson.listIcon = createListIconBottom(0)
        itemCard.positionIconChoose = 0

        mListItemCard.clear()
        mListItemCard.add(ItemCard(CardAdapter.TYPE_LOADING))
        mListItemCard.add(itemCard)
        mListItemCard.add(ItemCard(CardAdapter.TYPE_LOADING))
        mLiveDataListItemCard.value = mListItemCard
    }

    fun updateListWhenClickItemBottom(positionOfCard: Int, positionIconInCard: Int) {
        var itemCard = ItemCard(CardAdapter.TYPE_ITEM_CARD)
        itemCard.itemPerson.listIcon = createListIconBottom(positionIconInCard)
        itemCard.positionIconChoose = positionIconInCard
        mListItemCard.set(positionOfCard, itemCard)
        mLiveDataListItemCard.value = mListItemCard
    }

    fun detechUserScroll(dx: Int, positionUserScrollTo: Int) {
        if (dx > 0) {
            // user swipe right
            mIsUserScrollLeft = false
        } else if (dx < 0) {
            // user swipe left
            mIsUserScrollLeft = true
        }
        mCurrentRvPosition = positionUserScrollTo
    }

    fun checkScrollCircularRv(state: Int) {
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            if (mCurrentRvPosition == (mListItemCard.size - 1)) {
                mLiveDataCircularScrollRv.value = 1
            } else if (mCurrentRvPosition == 0) {
                mLiveDataCircularScrollRv.value =mListItemCard.size - 2
            }
        }
    }

    private fun createListIconBottom(positionActiveIcon: Int): MutableList<ItemIcon> {
        var listIcon: MutableList<ItemIcon> = ArrayList()
        for (i in (0..3)) {
            var isChoose = false
            if (i == positionActiveIcon) {
                isChoose = true
            }
            when (i) {
                0 -> {
                    listIcon.add(
                        ItemIcon(
                            R.drawable.ic_name_not_active,
                            R.drawable.ic_name_active,
                            isChoose
                        )
                    )
                }
                1 -> {
                    listIcon.add(
                        ItemIcon(
                            R.drawable.ic_birthday_not_active,
                            R.drawable.ic_birthday_active,
                            isChoose
                        )
                    )
                }
                2 -> {
                    listIcon.add(
                        ItemIcon(
                            R.drawable.ic_location_not_active,
                            R.drawable.ic_location_active,
                            isChoose
                        )
                    )
                }
                3 -> {
                    listIcon.add(
                        ItemIcon(
                            R.drawable.ic_phone_not_active,
                            R.drawable.ic_phone_active,
                            isChoose
                        )
                    )
                }
            }
        }
        return listIcon
    }
}