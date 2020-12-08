package com.team.home_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.team.home_page.adapter.CardAdapter
import com.team.home_page.model.ItemCard
import com.team.home_page.viewmodel.HomePageViewModel
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePageActivity : AppCompatActivity(), CardAdapter.IconBottomClick {

    private var mHomePageViewModel: HomePageViewModel? = null
    private var mCardAdapter: CardAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        initViews()
        addObserver()
        addEvents()
        mHomePageViewModel?.getListItemCard()
    }

    private fun addEvents() {
        rvCard.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mHomePageViewModel?.detechUserScroll(
                    dx,
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                )
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                mHomePageViewModel?.checkScrollCircularRv(newState)
            }
        })
    }

    private fun addObserver() {
        mHomePageViewModel?.getLiveDataCircularScrollRv()?.observe(this, Observer { position ->
            rvCard.scrollToPosition(position)
        })

        mHomePageViewModel?.getLiveDataListItemCard()?.observe(this, Observer { listItemCard ->
            mCardAdapter?.updateList(listItemCard)
        })

    }

    private fun initViews() {
        initViewModel()
        initRvCard()
    }

    private fun initRvCard() {
        var listItemCard: MutableList<ItemCard> = ArrayList()
        rvCard.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        mCardAdapter = CardAdapter(listItemCard, this)
        rvCard.adapter = mCardAdapter
        PagerSnapHelper().attachToRecyclerView(rvCard)
        mCardAdapter?.updateList(listItemCard)
    }

    private fun initViewModel() {
        mHomePageViewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
    }

    override fun onItemBottomClick(positionOfCard: Int, positionIconInCard: Int) {
        mHomePageViewModel?.updateListWhenClickItemBottom(positionOfCard, positionIconInCard)
    }
}