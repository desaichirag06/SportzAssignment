package com.chirag.sportzassignment.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chirag.sportzassignment.R
import com.chirag.sportzassignment.databinding.TeamFilterSelectionBinding
import com.chirag.sportzassignment.model.TeamListFilterData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TeamDisplaySelection(
    private val teamFilterArrayList: ArrayList<TeamListFilterData>,
    private val listener: (TeamListFilterData) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var mBinding: TeamFilterSelectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.team_filter_selection, container, false)

        mBinding.layoutTeamA.team = teamFilterArrayList[0]
        mBinding.layoutTeamB.team = teamFilterArrayList[1]

        mBinding.layoutTeamA.ivTeamSelected.visibility =
            if (teamFilterArrayList[0].isSelected) View.VISIBLE else View.GONE
        mBinding.layoutTeamB.ivTeamSelected.visibility =
            if (teamFilterArrayList[1].isSelected) View.VISIBLE else View.GONE

        mBinding.layoutTeamA.clTeam.setOnClickListener {
            teamFilterArrayList[0].isSelected = !teamFilterArrayList[0].isSelected
            mBinding.layoutTeamA.ivTeamSelected.visibility =
                if (teamFilterArrayList[0].isSelected) View.VISIBLE else View.GONE
            dismiss()
            listener(teamFilterArrayList[0])
        }

        mBinding.layoutTeamB.clTeam.setOnClickListener {
            teamFilterArrayList[1].isSelected = !teamFilterArrayList[1].isSelected
            mBinding.layoutTeamB.ivTeamSelected.visibility =
                if (teamFilterArrayList[1].isSelected) View.VISIBLE else View.GONE
            dismiss()
            listener(teamFilterArrayList[1])
        }

        return mBinding.root
    }
}
