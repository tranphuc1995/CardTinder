package com.tranphuc.home_page.viewmodel

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
import com.tranphuc.home_page.model.ItemPerson
import kotlinx.coroutines.*


class HomePageViewModel(
    private val getPersonFromRemoteUsecase: GetPersonFromRemoteUsecase,
    private val personToItemPerson: PersonToItemPerson,
    private val savePersonToRoomUsecase: SavePersonToRoomUsecase,
    private val getListPersonFromRoomUsecase: GetListPersonFromRoomUsecase
) : ViewModel() {

    // Check network available
    private var misNetworkAvailable = false

    // Detect user scroll left or right
    private var mIsUserScrollLeft = false

    // Detech position user scroll recyclerview to
    private var mCurrentRvPosition = 0

    // Detech x axis (scrolling direction of user)
    private var mDx = 0

    private var mListItemCard: MutableList<ItemCard> = ArrayList()
    private var mJob: Job? = null

    // LiveData
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
        if (misNetworkAvailable) {
            // load data from remote
            mListItemCard.add(ItemCard(CardAdapter.TYPE_LOADING))
            mListItemCard.add(ItemCard(CardAdapter.TYPE_LOADING))
            mListItemCard.add(ItemCard(CardAdapter.TYPE_LOADING))
            mLiveDataListItemCard.value = mListItemCard
            mLiveDataCircularScrollRv.value = 1
            doUsecaseGetPersonFromRemote()
        } else {
            // load data from local
            doUsecaseLoadPersonFromLocal()
        }
    }


    fun detechUserScroll(dx: Int, positionUserScrollTo: Int) {
        mDx = dx
        if (mDx > 0) {
            // user swipe right
            mIsUserScrollLeft = false
        } else if (mDx < 0) {
            // user swipe left
            mIsUserScrollLeft = true
        }
        mCurrentRvPosition = positionUserScrollTo
    }

    fun updateStateNetwork(hasConnection: Boolean) {
        misNetworkAvailable = hasConnection
    }

    fun checkScrollCircularRv(state: Int) {
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            mJob = CoroutineScope(Dispatchers.IO).launch {
                //only handle logic swipe left & right when internet is ready.
                if (misNetworkAvailable) {
                    if (mCurrentRvPosition != -1 && mCurrentRvPosition != 1) {
                        if (mIsUserScrollLeft) {
                            // get new person from remote
                            withContext(Dispatchers.Main) {
                                getNewPersonWhenUserSwipeRightLeft()
                            }
                        } else {
                            // bookmark person
                            if (mListItemCard[1].itemPerson.id != "") {
                                doUsecaseSavePersonToRoom()

                                // show toast
                                withContext(Dispatchers.Main) {
                                    mLiveDataShowToast.value =
                                        "Bookmark user: " + mListItemCard[1].itemPerson.name
                                    getNewPersonWhenUserSwipeRightLeft()
                                }
                            }
                        }
                        delay(200)
                    }
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

    private fun doUsecaseLoadPersonFromLocal() {
        CoroutineScope(Dispatchers.IO).launch {
            val listPerson = getListPersonFromRoomUsecase.excute()
            val listItemPerson =
                listPerson.map { person -> personToItemPerson.map(person) } as MutableList<ItemPerson>
            if (listItemPerson.size == 0) {
                // add view no data
                mListItemCard.add(ItemCard(CardAdapter.TYPE_NO_DATA))
            } else {
                listItemPerson.forEach { itemPerson ->
                    val itemCard = ItemCard(CardAdapter.TYPE_ITEM_CARD)
                    itemCard.itemPerson = itemPerson
                    mListItemCard.add(itemCard)
                }
            }
            withContext(Dispatchers.Main) {
                mLiveDataListItemCard.value = mListItemCard
            }
        }
    }

    private suspend fun doUsecaseSavePersonToRoom() {
        savePersonToRoomUsecase.excute(
            mListItemCard[1].itemPerson.id,
            mListItemCard[1].itemPerson.avatar,
            mListItemCard[1].itemPerson.name,
            mListItemCard[1].itemPerson.address,
            mListItemCard[1].itemPerson.phone,
            mListItemCard[1].itemPerson.birthDay
        )
    }

    private fun doUsecaseGetPersonFromRemote() {
        mJob = CoroutineScope(Dispatchers.IO).launch {
            val person = getPersonFromRemoteUsecase.excute()
            val itemPerson = personToItemPerson.map(person)
            // update card person
            val itemCard = ItemCard(CardAdapter.TYPE_ITEM_CARD)
            itemCard.itemPerson = itemPerson
            mListItemCard[1] = itemCard
            withContext(Dispatchers.Main) {
                mLiveDataListItemCard.value = mListItemCard
            }
        }
    }

    private fun getNewPersonWhenUserSwipeRightLeft() {
        mListItemCard[1] = ItemCard(CardAdapter.TYPE_LOADING)
        mLiveDataListItemCard.value = mListItemCard
        doUsecaseGetPersonFromRemote()
    }

    override fun onCleared() {
        super.onCleared()
        // cancel coroutines
        mJob?.cancel()
    }
}