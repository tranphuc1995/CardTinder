package com.tranphuc.home_page.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.tranphuc.domain.usecases.GetListPersonFromRoomUsecase
import com.tranphuc.domain.usecases.GetPersonFromRemoteUsecase
import com.tranphuc.domain.usecases.SavePersonToRoomUsecase
import com.tranphuc.home_page.adapter.CardAdapter
import com.tranphuc.home_page.mapper.PersonToItemPerson
import com.tranphuc.home_page.model.ItemCard
import com.tranphuc.home_page.utils.NetworkUtils
import kotlinx.coroutines.*

class HomePageViewModel(
    val getPersonFromRemoteUsecase: GetPersonFromRemoteUsecase,
    val personToItemPerson: PersonToItemPerson,
    val savePersonToRoomUsecase: SavePersonToRoomUsecase,
    val getListPersonFromRoomUsecase: GetListPersonFromRoomUsecase,
    val context: Context
) : ViewModel() {

    private var mIsUserScrollLeft = false
    private var mCurrentRvPosition = 0
    private var mListItemCard: MutableList<ItemCard> = ArrayList()
    private var mJob: Job? = null

    private var mLiveDataListItemCard: MutableLiveData<List<ItemCard>> = MutableLiveData()
    private var mLiveDataCircularScrollRv: MutableLiveData<Int> = MutableLiveData()
    private var mLiveDataShowToast: MutableLiveData<String> = MutableLiveData()

    fun getLiveDataListItemCard(): LiveData<List<ItemCard>> {
        return mLiveDataListItemCard
    }

    fun getLiveDataCircularScrollRv(): LiveData<Int> {
        return mLiveDataCircularScrollRv
    }

    fun getLiveDataShowToast(): LiveData<String> {
        return mLiveDataShowToast
    }

    fun getListItemCard() {
        if (NetworkUtils.isNetworkAvailable(context)) {
            // load data from remote
            mListItemCard.add(ItemCard(CardAdapter.TYPE_LOADING))
            mListItemCard.add(ItemCard(CardAdapter.TYPE_LOADING))
            mListItemCard.add(ItemCard(CardAdapter.TYPE_LOADING))
            mLiveDataListItemCard.value = mListItemCard
            mLiveDataCircularScrollRv.value = 1
            doUsecaseGetPersonFromRemote()
        } else {
            // load data from local

        }
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
            mJob = CoroutineScope(Dispatchers.IO).launch {
                if (NetworkUtils.isNetworkAvailable(context)) {
                    //only handle logic swipe left & right when internet is ready.
                    if (mIsUserScrollLeft) {
                        // get new person from remote
                        if (mCurrentRvPosition != -1 && mCurrentRvPosition != 1) {
                            withContext(Dispatchers.Main) {
                                getNewPersonWhenUserSwipeRightLeft()
                            }

                        }
                    } else {
                        // bookmark person
                        savePersonToRoomUsecase.excute(
                            mListItemCard.get(1).itemPerson.id,
                            mListItemCard.get(1).itemPerson.avatar,
                            mListItemCard.get(1).itemPerson.name,
                            mListItemCard.get(1).itemPerson.address,
                            mListItemCard.get(1).itemPerson.phone,
                            mListItemCard.get(1).itemPerson.birthDay
                        )

                        // show toast
                        withContext(Dispatchers.Main) {
                            mLiveDataShowToast.value =
                                "Bookmark user: " + mListItemCard.get(1).itemPerson.name
                            getNewPersonWhenUserSwipeRightLeft()
                        }
                    }
                    delay(200)
                }
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

    private fun getNewPersonWhenUserSwipeRightLeft() {
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