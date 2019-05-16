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
import com.aliumujib.flightyy.ui.adapters.base.BindableItemClickListener
import com.aliumujib.flightyy.ui.map.MapsFragmentArgs
import com.aliumujib.flightyy.ui.utils.NavigationCommand
import java.text.SimpleDateFormat
import java.util.*

class ScheduleListFragment : BaseFragment(), BindableItemClickListener<ScheduleModel> {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var searchFlightsViewModel: SearchFlightsViewModel

    @Inject
    lateinit var schedulesListAdapter: SchedulesListAdapter

    override fun getViewModel(): BaseViewModel {
        return searchFlightsViewModel
    }

    override fun onItemClicked(data: ScheduleModel) {
        navigate(NavigationCommand.To(ScheduleListFragmentDirections.actionScheduleListFragmentToMapsFragment(data)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)

        searchFlightsViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchFlightsViewModel::class.java)

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
        arguments?.let {
            val origin = ScheduleListFragmentArgs.fromBundle(it).origin
            val destination = ScheduleListFragmentArgs.fromBundle(it).destination
            val date = Date(ScheduleListFragmentArgs.fromBundle(it).date)

            origin_text.text = context?.getString(R.string.city_format, origin.code, origin.state)
            destination_text.text = context?.getString(R.string.city_format, destination.code, destination.state)
            date_text.text = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).format(date)

            searchFlightsViewModel.searchFlights(origin, destination, date)
        }

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


        searchFlightsViewModel.liveData.observe(viewLifecycleOwner, Observer {
            it.data?.let { list ->
                schedulesListAdapter.setData(list)
            }
        })
    }

}
