package com.example.samplekotlinapplication.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.downloadcoroutines.utils.Preferences
import com.nexogic.base.BaseActivity

open class BaseFragment : Fragment() {
    lateinit var preference: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = Preferences(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun showDialog() {
        (context as BaseActivity).dialog.show()
    }

    fun dismissDialog() {
        (context as BaseActivity).dialog.dismiss()
    }


}