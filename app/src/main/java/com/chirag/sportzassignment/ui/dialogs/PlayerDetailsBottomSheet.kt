package com.chirag.sportzassignment.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.chirag.sportzassignment.R
import com.chirag.sportzassignment.databinding.PlayerDetailsBottomsheetBinding
import com.chirag.sportzassignment.model.PlayerData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PlayerDetailsBottomSheet(
    private var playerDetail: PlayerData
) : BottomSheetDialogFragment() {

    lateinit var mBinding: PlayerDetailsBottomsheetBinding

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //bottom sheet round corners can be obtained but the while background appears to remove that we need to add this.
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.player_details_bottomsheet,
            container,
            false
        )

        mBinding.player = playerDetail
        mBinding.executePendingBindings()
        mBinding.ivClose.setOnClickListener { dialog!!.dismiss() }

        return mBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.player_details_bottomsheet, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        bottomSheetDialog.setOnShowListener {
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let {
                val behavior = BottomSheetBehavior.from(it)
                val bottomSheetHeight = (resources.displayMetrics.heightPixels * 0.9).toInt()
                behavior.peekHeight = bottomSheetHeight
            }
        }

        return bottomSheetDialog
    }
}