package com.example.samplekotlinapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.samplekotlinapplication.adapters.GenericAdapter
import com.example.samplekotlinapplication.utils.showToast
import com.nexogic.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), GenericAdapter.OnItemClickListener<Any> {
    private lateinit var picsAdapter: GenericAdapter
    val compositeDisposable by lazy { CompositeDisposable() }
    var page = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showDialog()
        getPics(page, 100)
        containerNested.setOnScrollChangeListener(nestedScrollListener)
        setClickListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun getPics(page: Int, limit: Int) {
        compositeDisposable.add(
            serviceBuilder.getPics(page, limit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response -> fetchPics(response) }, { t -> onError(t) })
        )
    }

    private fun setClickListeners() {
        fbJavaActivity.setOnClickListener {
            startActivity(Intent(this, JavaActivity::class.java))
        }
        showToast("something")
    }

    private fun fetchPics(picsList: ArrayList<SpecialistsModel>) {
        if (!::picsAdapter.isInitialized) {
            picsAdapter = GenericAdapter(picsList as ArrayList<Any>, this, R.layout.row_bacground)
        }
        var gridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        var secondAdapter =
            GenericAdapter(picsList as ArrayList<Any>, this, R.layout.row_background_2)

        var concatAdapter = ConcatAdapter();

        concatAdapter.addAdapter(secondAdapter)
        concatAdapter.addAdapter(picsAdapter)
        rvHome.apply {
            layoutManager =
                gridLayoutManager
            adapter = concatAdapter
            setItemViewCacheSize(picsList.size)
        }
        dismissDialog()
    }

    private fun onError(throwable: Throwable) {
        dismissDialog()
        showToast(throwable.localizedMessage)
    }

    private var nestedScrollListener =
        NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v!!.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                page++
                getPics(page, 100)
            }
        }

    override fun onItemClick(view: View?, position: Int, `object`: Any) {
    }
}