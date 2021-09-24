package com.nexogic.base


import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.FragmentActivity
import com.example.downloadcoroutines.utils.Preferences
import com.example.samplekotlinapplication.R
import com.example.samplekotlinapplication.apiservices.ServiceBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_dialog.*

open class BaseActivity : FragmentActivity() {
    val preference by lazy { Preferences(this) }
    val serviceBuilder by lazy { ServiceBuilder.buildService() }
    private var mSnackBar: Snackbar? = null
    lateinit var animeList: ArrayList<String>
    val dialog by lazy { Dialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDialog()


    }

    private fun setDialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog)
        animeList = ArrayList()
        animeList.add("https://assets4.lottiefiles.com/packages/lf20_kJNwM4.json")
        animeList.add("https://assets8.lottiefiles.com/packages/lf20_x62chJ.json")
        animeList.add("https://assets8.lottiefiles.com/packages/lf20_Z4BhGL.json")
        animeList.add("https://assets8.lottiefiles.com/packages/lf20_jny63J.json")
        animeList.add("https://assets8.lottiefiles.com/packages/lf20_rPGSco.json")
        animeList.add("https://assets8.lottiefiles.com/packages/lf20_YMim6w.json")

        dialog.let {
            it.animeLoading.setAnimationFromUrl(animeList[0])
        }
    }


    fun showDialog() {
        dialog.show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }


}