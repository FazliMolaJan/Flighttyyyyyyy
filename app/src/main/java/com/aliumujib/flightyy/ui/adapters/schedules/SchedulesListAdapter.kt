package com.aliumujib.flightyy.ui.adapters.schedules

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aliumujib.flightyy.R
import com.aliumujib.flightyy.presentation.models.schedule.FlightModel
import com.aliumujib.flightyy.presentation.models.schedule.ScheduleModel
import com.aliumujib.flightyy.ui.adapters.base.BindableAdapter
import com.aliumujib.flightyy.ui.adapters.base.BindableItemClickListener
import com.aliumujib.flightyy.ui.adapters.base.SingleLayoutAdapter
import com.aliumujib.flightyy.ui.utils.DiffUtilCallback
import kotlinx.android.synthetic.main.schedule_item.view.*
import kotlin.properties.Delegates
import kotlin.random.Random

class SchedulesListAdapter() : RecyclerView.Adapter<ScheduleViewHolder>(), BindableAdapter<ScheduleModel>{

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScheduleViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.schedule_item, parent, false)
        return ScheduleViewHolder(v, viewPool)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val parent = items[position]
        holder.bindViews(parent)
    }


    var items by Delegates.observable<List<ScheduleModel>>(listOf()) { _, oldValue, newValue ->
        DiffUtil
            .calculateDiff(DiffUtilCallback(oldValue, newValue))
            .dispatchUpdatesTo(this)
    }



    override fun setData(data: List<ScheduleModel>) {
        items = data
    }

}


class ScheduleViewHolder(
    itemView: View,
    private var viewPool: RecyclerView.RecycledViewPool
) : RecyclerView.ViewHolder(itemView) {


    fun bindViews(scheduleModel: ScheduleModel) {

        itemView.flights.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
            setRecycledViewPool(viewPool)
            val flightAdapter = SingleLayoutAdapter<FlightModel>(R.layout.flight_item)
            adapter = flightAdapter
            flightAdapter.setData(scheduleModel.flightModels)
        }

        itemView.airlines.text = scheduleModel.flightModels[0].airlineModel.name
        itemView.price.text = "$${Random(2000).nextInt()}"
    }

    companion object {
        fun create(
            @LayoutRes layoutID: Int, parent: ViewGroup,
            viewPool: RecyclerView.RecycledViewPool
        ): ScheduleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(layoutID, parent, false)
            return ScheduleViewHolder(view, viewPool)
        }
    }
}