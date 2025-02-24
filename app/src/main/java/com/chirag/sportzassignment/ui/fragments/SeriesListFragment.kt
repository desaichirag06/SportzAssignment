package com.chirag.sportzassignment.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.chirag.sportzassignment.R
import com.chirag.sportzassignment.databinding.FragmentSeriesListBinding
import com.chirag.sportzassignment.misc.Constants
import com.chirag.sportzassignment.misc.Constants.Companion.IND_VS_NZ_ENDPOINT
import com.chirag.sportzassignment.misc.Constants.Companion.PAK_VS_SA_ENDPOINT
import com.chirag.sportzassignment.model.SeriesData
import com.chirag.sportzassignment.model.TeamData
import com.chirag.sportzassignment.ui.adapters.SeriesListAdapter
import com.chirag.sportzassignment.ui.dialogs.NoInternetBottomSheet
import com.chirag.sportzassignment.webservice.CustomRetrofit
import com.chirag.sportzassignment.webservice.JSONCallback
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class SeriesListFragment : BaseFragment() {

    private lateinit var mBinding: FragmentSeriesListBinding
    private var seriesData: SeriesData? = null

    private var seriesListAdapter: SeriesListAdapter? = null
    val seriesList = ArrayList<SeriesData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_series_list, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        seriesListAdapter = null
        seriesList.clear()
        checkInternetAndCallApi { fetchSeriesDetails(PAK_VS_SA_ENDPOINT) }
        checkInternetAndCallApi { fetchSeriesDetails(IND_VS_NZ_ENDPOINT) }
    }

    private fun fetchSeriesDetails(endPoint: String) {

        try {
            CustomRetrofit.with().setUrl(Constants.BASE_URL).setAPI(endPoint).setGetParameters(null)
                .setCallBackListener(object : JSONCallback(mContext, null) {
                    /**Handle API failure */
                    override fun onFailed(statusCode: Int, message: String?) {
                        /**Handle Network connection exception */
                        if (message == getString(R.string.no_internet_connection)) {
                            NoInternetBottomSheet(
                                mContext
                            ) { isRetryClicked: Boolean ->
                                when {
                                    isRetryClicked -> checkInternetAndCallApi {
                                        fetchSeriesDetails(
                                            endPoint
                                        )
                                    }

                                    else -> mActivity.finish()
                                }
                            }.show(mActivity.supportFragmentManager, "no_internet_connection")
                        } else {
                            Toast.makeText(
                                mContext,
                                getString(R.string.failed_to_load_data_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    /**Handle API success */
                    override fun onSuccess(statusCode: Int, jsonObject: JSONObject?) {
                        try {
                            seriesData = Gson().fromJson(
                                jsonObject!!.toString(), object : TypeToken<SeriesData?>() {}.type
                            )
                            seriesList.add(seriesData!!)
                            updateRecyclerView()
                        } catch (e: Exception) {
                            /**Handle Network connection exception */
                            if (e.message == getString(R.string.no_internet_connection)) {
                                NoInternetBottomSheet(mContext) { isRetryClicked: Boolean ->
                                    when {
                                        isRetryClicked -> checkInternetAndCallApi {
                                            fetchSeriesDetails(
                                                endPoint
                                            )
                                        }

                                        else -> mActivity.finish()
                                    }
                                }.show(mActivity.supportFragmentManager, "no_internet_connection")
                            } else {
                                Toast.makeText(
                                    mContext,
                                    getString(R.string.failed_to_load_data_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    }

                })
        } catch (e: Exception) {
            /**Handle API Response exception */
            if (e.message == getString(R.string.no_internet_connection)) {
                NoInternetBottomSheet(mContext) { isRetryClicked: Boolean ->
                    when {
                        isRetryClicked -> checkInternetAndCallApi {
                            fetchSeriesDetails(
                                endPoint
                            )
                        }

                        else -> mActivity.finish()
                    }
                }.show(mActivity.supportFragmentManager, "no_internet_connection")
            } else {
                Toast.makeText(
                    mContext,
                    getString(R.string.failed_to_load_data_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateRecyclerView() {
        // Notify the adapter that the data has changed and needs to be updated
        if (seriesListAdapter == null) {
            seriesListAdapter = SeriesListAdapter(mContext, seriesList, navigateToTeams)
            mBinding.rvSeriesList.adapter = seriesListAdapter
        } else {
            /**
            DiffUtils can be used for better performance but
            currently using notifyDataSetChanged() as the list is limited
             */
            seriesListAdapter!!.notifyDataSetChanged()
        }
    }

    private var navigateToTeams: SeriesListAdapter.OnClickItem =
        object : SeriesListAdapter.OnClickItem {
            override fun redirect(seriesData: SeriesData) {
                navigateToTeamsScreen(seriesData)
            }
        }

    private fun navigateToTeamsScreen(seriesData: SeriesData) {
        val teamList: Array<TeamData> = seriesData.getTeamsList().toTypedArray()
        val action =
            SeriesListFragmentDirections.actionSeriesToTeamsFragment(teamList)
        findNavController(this).navigate(action)
    }

}