package com.tranphuc.home_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.tranphuc.home_page.adapter.CardAdapter
import com.tranphuc.home_page.model.ItemCard
import com.tranphuc.home_page.viewmodel.HomePageViewModel
import kotlinx.android.synthetic.main.activity_home_page.*
import org.koin.android.ext.android.get

class HomePageActivity : AppCompatActivity() {

    val mHomePageViewModel = get<HomePageViewModel>()
    private var mCardAdapter: CardAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        initViews()
        addObserver()
        addEvents()
        mHomePageViewModel.getListItemCard()
    }

    private fun addEvents() {
        rvCard.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mHomePageViewModel.detechUserScroll(
                    dx,
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                )
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                mHomePageViewModel.checkScrollCircularRv(newState)
            }
        })
    }

    private fun addObserver() {
        mHomePageViewModel.getLiveDataCircularScrollRv().observe(this, Observer { position ->
            rvCard.scrollToPosition(position)
        })

        mHomePageViewModel.getLiveDataListItemCard().observe(this, Observer { listItemCard ->
            mCardAdapter?.updateList(listItemCard)
        })

        mHomePageViewModel.getLiveDataShowToast().observe(this, Observer { content ->
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
        })

    }

    private fun initViews() {
        initRvCard()
    }

    private fun initRvCard() {
        var listItemCard: MutableList<ItemCard> = ArrayList()
        rvCard.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        mCardAdapter = CardAdapter(listItemCard)
        rvCard.adapter = mCardAdapter
        PagerSnapHelper().attachToRecyclerView(rvCard)
        mCardAdapter?.updateList(listItemCard)
    }
}