package com.chirag.sportzassignment.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.chirag.sportzassignment.R
import com.chirag.sportzassignment.databinding.LoaderLayoutBinding

class CustomProgressDialog(private val context: Context) : Dialog(
    context, R.style.ProgressDialog
) {
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        val mBinder = DataBindingUtil.inflate<LoaderLayoutBinding>(
            LayoutInflater.from(getContext()),
            R.layout.loader_layout,
            null,
            false
        )
        setContentView(mBinder.root)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    override fun onStart() {
        super.onStart()
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
