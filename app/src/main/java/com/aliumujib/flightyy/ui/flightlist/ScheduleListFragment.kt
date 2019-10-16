package com.aliumujib.flightyy.ui.flightlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aliumujib.flightyy.R
import com.aliumujib.flightyy.presentation.viewmodels.SearchFlightsViewModel
import com.aliumujib.flightyy.ui.adapters.schedules.SchedulesListAdapter
import com.aliumujib.flightyy.ui.base.BaseFragment
import com.aliumujib.flightyy.ui.base.BaseViewModel
import com.aliumujib.flightyy.ui.inject.ViewModelFactory
import com.aliumujib.flightyy.ui.utils.SpacingItemDecoration
import com.aliumujib.flightyy.ui.utils.ext.dpToPx
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_schedule_list.*
import javax.inject.Inject
import androidx.lifecycle.Observer
import com.aliumujib.flightyy.data.remote.models.Schedule
import com.aliumujib.flightyy.presentation.models.schedule.ScheduleModel
import com.aliumujib.flightyy.presentation.state.Resource
import com.aliumujib.flightyy.presentation.state.Status
import com.aliumujib.flightyy.ui.adapters.base.BindableItemClickListener
import com.aliumujib.flightyy.ui.map.MapsFragmentArgs
import com.aliumujib.flightyy.ui.utils.NavigationCommand
import com.aliumujib.flightyy.ui.utils.ext.findNavController
import com.aliumujib.flightyy.ui.utils.ext.showSnackbar
import com.aliumujib.flightyy.ui.utils.observe
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textResource
import java.text.SimpleDateFormat
import java.util.*

class ScheduleListFragment : BaseFragment(), BindableItemClickListener<ScheduleModel> {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SearchFlightsViewModel

    @Inject
    lateinit var schedulesListAdapter: SchedulesListAdapter

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun onItemClicked(data: ScheduleModel) {
        navigate(
            NavigationCommand.To(
                ScheduleListFragmentDirections.actionScheduleListFragmentToMapsFragment(
                    data
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SearchFlightsViewModel::class.java)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        runQuery()

        schedules_rv.apply {
            this.addItemDecoration(
                SpacingItemDecoration(
                    context.dpToPx(16),
                    context.dpToPx(16),
                    doubleFirstItemLeftMargin = false,
                    isVertical = true
                )
            )
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = schedulesListAdapter
        }


        observe(viewModel.schedules, ::handleSchedules)
    }

    private fun runQuery() {
        arguments?.let {
            val origin = ScheduleListFragmentArgs.fromBundle(it).origin
            val destination = ScheduleListFragmentArgs.fromBundle(it).destination
            val date = ScheduleListFragmentArgs.fromBundle(it).date

            origin_text.text = context?.getString(R.string.city_format, origin.code, origin.state)
            destination_text.text =
                context?.getString(R.string.city_format, destination.code, destination.state)
            date_text.text = date

            viewModel.searchFlights(origin, destination, date)
        }
    }

    private fun handleSchedules(resource: Resource<List<ScheduleModel>>?) {
        resource?.let { data ->
            when {
                data.status == Status.LOADING -> {
                    showLoadingView()
                }
                data.status == Status.ERROR -> showErrorState(data)
                else -> progress_horizontal.visibility = View.GONE
            }

            data.message?.let {
                showSnackbar(it, Snackbar.LENGTH_SHORT)
            }

            data.data?.let { list ->
                if (list.isEmpty()) {
                    showEmptyState()
                } else {
                    state_view.visibility = View.GONE
                }
                schedulesListAdapter.setData(list)
            }
        }
    }

    private fun showEmptyState() {
        state_view.visibility = View.VISIBLE
        retry_btn.visibility = View.GONE
        state_image.imageResource = R.drawable.ic_inbox_24dp
        state_text.textResource = R.string.no_data_available
    }

    private fun showLoadingView() {
        progress_horizontal.visibility = View.VISIBLE
        state_view.visibility = View.GONE
    }

    private fun showErrorState(data: Resource<List<ScheduleModel>>) {
        progress_horizontal.visibility = View.GONE
        state_image.imageResource = R.drawable.ic_error_black_24dp
        state_text.text = data.message
        state_view.visibility = View.VISIBLE
        retry_btn.visibility = View.VISIBLE
        retry_btn.setOnClickListener {
            runQuery()
        }
    }


}
