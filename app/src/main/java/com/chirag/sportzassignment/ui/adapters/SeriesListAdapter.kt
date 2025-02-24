package com.chirag.sportzassignment.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chirag.sportzassignment.R
import com.chirag.sportzassignment.databinding.ItemSeriesLayoutBinding
import com.chirag.sportzassignment.model.SeriesData

class SeriesListAdapter(
    private var context: Context, private var seriesList: ArrayList<SeriesData>,
    private var click: OnClickItem
) : RecyclerView.Adapter<SeriesListAdapter.ViewHolder>() {

    private lateinit var mBinding: ItemSeriesLayoutBinding

    class ViewHolder(var mBinder: ItemSeriesLayoutBinding) : RecyclerView.ViewHolder(mBinder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.item_series_layout, parent, false
        )
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = seriesList[position]
        holder.mBinder.match = current.Matchdetail.Match
        holder.mBinder.series = current.Matchdetail.Series
        holder.mBinder.venue = current.Matchdetail.Venue
        holder.mBinder.executePendingBindings()
        holder.mBinder.ivRedirect.setOnClickListener {
            click.redirect(current)
        }
    }

    override fun getItemCount(): Int {
        return if (seriesList.isEmpty()) 0
        else seriesList.size
    }

    interface OnClickItem {
        fun redirect(seriesData: SeriesData)
    }

}
