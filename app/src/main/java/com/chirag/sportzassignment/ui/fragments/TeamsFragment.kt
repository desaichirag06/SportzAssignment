package com.chirag.sportzassignment.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chirag.sportzassignment.R
import com.chirag.sportzassignment.databinding.FragmentTeamsBinding
import com.chirag.sportzassignment.model.PlayerData
import com.chirag.sportzassignment.model.TeamData
import com.chirag.sportzassignment.model.TeamListFilterData
import com.chirag.sportzassignment.ui.adapters.TeamListAdapter
import com.chirag.sportzassignment.ui.dialogs.PlayerDetailsBottomSheet
import com.chirag.sportzassignment.ui.dialogs.TeamDisplaySelection

class TeamsFragment : BaseFragment() {

    private lateinit var mBinding: FragmentTeamsBinding

    private val args: TeamsFragmentArgs by navArgs()
    private lateinit var teamList: List<TeamData>
    private var teamListAdapter: TeamListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_teams, container, false)

        val teamArray = args.teamList
        teamList = teamArray.toList()
        initView()
        return mBinding.root
    }

    private fun initView() {
        mBinding.btnTeamA.text = teamList[0].fullName
        mBinding.btnTeamB.text = teamList[1].fullName

        val teamFilterList = ArrayList<TeamListFilterData>()
        teamFilterList.add(
            TeamListFilterData(
                id = teamList[0].teamId.toInt(),
                teamName = teamList[0].fullName,
                isSelected = true
            )
        )
        teamFilterList.add(
            TeamListFilterData(
                id = teamList[1].teamId.toInt(),
                teamName = teamList[1].fullName,
                isSelected = true
            )
        )

        setTeamA()
        mBinding.btnTeamA.setOnClickListener { setTeamA() }
        mBinding.btnTeamB.setOnClickListener { setTeamB() }

        mBinding.ivFilter.setOnClickListener {
            // Ensure teamFilterList reflects the current selection
            teamFilterList.forEach { model ->
                model.isSelected = when (model.id) {
                    teamList[0].teamId.toInt() -> mBinding.btnTeamA.visibility == View.VISIBLE
                    teamList[1].teamId.toInt() -> mBinding.btnTeamB.visibility == View.VISIBLE
                    else -> false
                }
            }

            // Open BottomSheet with updated list
            TeamDisplaySelection(teamFilterList) { model ->
                if (model.id == teamList[0].teamId.toInt()) {
                    if (model.isSelected) {
                        mBinding.btnTeamA.visibility = View.VISIBLE
                        setTeamA()
                    } else {
                        mBinding.btnTeamA.visibility = View.GONE
                        if (teamFilterList[1].isSelected) {
                            setTeamB()
                        } else {
                            mBinding.rvPlayers.visibility = View.GONE
                            mBinding.tvNoRecords.visibility = View.VISIBLE
                        }
                    }
                } else {
                    if (model.isSelected) {
                        setTeamB()
                        mBinding.btnTeamB.visibility = View.VISIBLE
                    } else {
                        mBinding.btnTeamB.visibility = View.GONE
                        if (teamFilterList[0].isSelected) {
                            setTeamA()
                        } else {
                            mBinding.rvPlayers.visibility = View.GONE
                            mBinding.tvNoRecords.visibility = View.VISIBLE
                        }
                    }
                }
            }.show(mActivity.supportFragmentManager, "bottom_sheet_fragment_tag")
        }
    }

    private fun setTeamA() {
        setRecyclerView(mContext, teamList[0].getPlayerList())
        mBinding.btnTeamA.setBackgroundResource(R.drawable.btn_selected)
        mBinding.btnTeamB.setBackgroundResource(R.drawable.btn_unselected)

    }

    private fun setTeamB() {
        setRecyclerView(mContext, teamList[1].getPlayerList())
        mBinding.btnTeamA.setBackgroundResource(R.drawable.btn_unselected)
        mBinding.btnTeamB.setBackgroundResource(R.drawable.btn_selected)
    }

    private fun setRecyclerView(mContext: Context, playerList: ArrayList<PlayerData>) {
        mBinding.rvPlayers.visibility = View.VISIBLE
        mBinding.tvNoRecords.visibility = View.GONE
        teamListAdapter = TeamListAdapter(mContext, playerList, showPlayerInfo)
        val dividerItemDecoration =
            DividerItemDecoration(mBinding.rvPlayers.context, LinearLayoutManager.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.divider_white
            )!!
        )
        mBinding.rvPlayers.addItemDecoration(dividerItemDecoration)
        mBinding.rvPlayers.setItemAnimator(DefaultItemAnimator())
        mBinding.rvPlayers.adapter = teamListAdapter
    }


    private var showPlayerInfo: TeamListAdapter.OnClickItem =
        object : TeamListAdapter.OnClickItem {
            override fun redirect(player: PlayerData) {
                PlayerDetailsBottomSheet(
                    player
                ).show(mActivity.supportFragmentManager, "no_internet_connection")
            }
        }

}