package com.chirag.sportzassignment.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chirag.sportzassignment.R
import com.chirag.sportzassignment.databinding.ItemPlayerLayoutBinding
import com.chirag.sportzassignment.model.PlayerData

class TeamListAdapter(
    private var context: Context, private var teamList: ArrayList<PlayerData>,
    private var click: OnClickItem
) : RecyclerView.Adapter<TeamListAdapter.ViewHolder>() {

    private lateinit var mBinding: ItemPlayerLayoutBinding

    class ViewHolder(var mBinder: ItemPlayerLayoutBinding) : RecyclerView.ViewHolder(mBinder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.item_player_layout, parent, false
        )
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = teamList[position]
        holder.mBinder.player = current
        holder.mBinder.executePendingBindings()
        holder.mBinder.ivRedirect.setOnClickListener {
            click.redirect(current)
        }
    }

    override fun getItemCount(): Int {
        return if (teamList.isEmpty()) 0
        else teamList.size
    }

    interface OnClickItem {
        fun redirect(player: PlayerData)
    }

}
