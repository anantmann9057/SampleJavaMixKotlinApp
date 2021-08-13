package com.example.samplekotlinapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.samplekotlinapplication.adapters.GenericAdapter
import com.example.samplekotlinapplication.apiservices.ServiceBuilder
import com.example.samplekotlinapplication.utils.showToast
import com.nexogic.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), GenericAdapter.OnItemClickListener<Any> {
    lateinit var picsAdapter: GenericAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showDialog()
        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(
            ServiceBuilder.buildService().getPics(1, 100)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response -> fetchPics(response) }, { t -> onError(t) })
        )
        setClickListeners()

    }

    private fun setClickListeners() {
        fbJavaActivity.setOnClickListener {
            startActivity(Intent(this, JavaActivity::class.java))
        }
    }

    private fun fetchPics(picsList: ArrayList<SpecialistsModel>) {
        if (!::picsAdapter.isInitialized) {
            picsAdapter = GenericAdapter(picsList as ArrayList<Any>, this, R.layout.row_bacground)
        }
        var gridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

        rvHome.apply {
            layoutManager =
                gridLayoutManager
            adapter = picsAdapter
            setItemViewCacheSize(picsList.size)
        }
        dismissDialog()
    }

    private fun onError(throwable: Throwable) {
        dismissDialog()
        showToast(throwable.localizedMessage)
    }

    override fun onItemClick(view: View?, position: Int, `object`: Any) {
    }
}