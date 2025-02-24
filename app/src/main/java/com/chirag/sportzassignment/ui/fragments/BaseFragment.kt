package com.chirag.sportzassignment.ui.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.chirag.sportzassignment.ui.activities.BaseActivity
import com.chirag.sportzassignment.ui.dialogs.NoInternetBottomSheet
import com.chirag.sportzassignment.utils.Utilities


open class BaseFragment : Fragment {
    lateinit var mContext: Context
    lateinit var mActivity: BaseActivity

    constructor()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = (activity as BaseActivity?)!!
    }

    fun checkInternetAndCallApi(function: () -> (Unit)) {
        if (!Utilities.isConnectingToInternet(mContext)) NoInternetBottomSheet(mContext) { isRetryClicked: Boolean ->
            if (isRetryClicked) {
                checkInternetAndCallApi(function)
            } else mActivity.finish()
        }.show(
            parentFragmentManager, "no_internet_connection"
        ) else function()
    }


}