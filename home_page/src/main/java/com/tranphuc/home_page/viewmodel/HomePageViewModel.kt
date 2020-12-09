package com.tranphuc.home_page.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.tranphuc.domain.usecases.GetPersonFromRemoteUsecase
import com.tranphuc.home_page.adapter.CardAdapter
import com.tranphuc.home_page.mapper.PersonToItemPerson
import com.tranphuc.home_page.model.ItemCard
import kotlinx.coroutines.*

class HomePageViewModel(
    val getPersonFromRemoteUsecase: GetPersonFromRemoteUsecase,
    val personToItemPerson: PersonToItemPerson
) : ViewModel() {
    private var mIsUserScrollLeft = false
    private var mCurrentRvPosition = 0
    private var mListItemCard: MutableList<ItemCard> = ArrayList()
    private var mJob: Job? = null

    private var mLiveDataListItemCard: MutableLiveData<List<ItemCard>> = MutableLiveData()
    private var mLiveDataCircularScrollRv: MutableLiveData<Int> = MutableLiveData()

    fun getLiveDataListItemCard(): LiveData<List<ItemCard>> {
        return mLiveDataListItemCard
    }

    fun getLiveDataCircularScrollRv(): LiveData<Int> {
        return mLiveDataCircularScrollRv
    }

    fun getListItemCard() {
        mListItemCard.add(ItemCard(CardAdapter.TYPE_LOADING))
        mListItemCard.add(ItemCard(CardAdapter.TYPE_LOADING))
        mListItemCard.add(ItemCard(CardAdapter.TYPE_LOADING))
        mLiveDataListItemCard.value = mListItemCard
        mLiveDataCircularScrollRv.value = 1
        doUsecaseGetPersonFromRemote()
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
            if (mIsUserScrollLeft) {
                if (mCurrentRvPosition != -1 && mCurrentRvPosition != 1) {
                    getPersonWhenUserSwipeLeft()
                }
            }
            CoroutineScope(Dispatchers.IO).launch {
                delay(200)
                withContext(Dispatchers.Main) {
                    if (mCurrentRvPosition == (mListItemCard.size - 1)) {
                        mLiveDataCircularScrollRv.value = 1
                    } else if (mCurrentRvPosition == 0) {
                        mLiveDataCircularScrollRv.value = mListItemCard.size - 2
                    }
                }
            }
        }
    }

    private fun doUsecaseGetPersonFromRemote() {
        mJob = CoroutineScope(Dispatchers.IO).launch {
            val person = getPersonFromRemoteUsecase.excute()
            var itemPerson = personToItemPerson.map(person)
            // update card person
            var itemCard = ItemCard(CardAdapter.TYPE_ITEM_CARD)
            itemCard.itemPerson = itemPerson
            mListItemCard.set(1, itemCard)
            withContext(Dispatchers.Main) {
                mLiveDataListItemCard.value = mListItemCard
            }
        }
    }

    private fun getPersonWhenUserSwipeLeft() {
        mListItemCard.set(1, ItemCard(CardAdapter.TYPE_LOADING))
        mLiveDataListItemCard.value = mListItemCard
        doUsecaseGetPersonFromRemote()
    }

    override fun onCleared() {
        super.onCleared()
        // cancel coroutines
        mJob?.cancel()
    }
}