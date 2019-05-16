package com.aliumujib.flightyy.ui.adapters.schedules

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class SchedulesListAdapter(var clickListener: BindableItemClickListener<ScheduleModel>) :
    RecyclerView.Adapter<ScheduleViewHolder>(), BindableAdapter<ScheduleModel> {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScheduleViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.schedule_item, parent, false)
        return ScheduleViewHolder(v, clickListener, viewPool)
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
    var clickListener: BindableItemClickListener<ScheduleModel>,
    private var viewPool: RecyclerView.RecycledViewPool
) : RecyclerView.ViewHolder(itemView) {


    fun bindViews(scheduleModel: ScheduleModel) {

        itemView.flights.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setRecycledViewPool(viewPool)

            //CLICKS
            val flightAdapter = SingleLayoutAdapter(R.layout.flight_item,object :BindableItemClickListener<FlightModel>{
                override fun onItemClicked(data: FlightModel) {
                    clickListener.onItemClicked(scheduleModel)
                }
            })

            adapter = flightAdapter
            flightAdapter.setData(scheduleModel.flightModels)
        }

        itemView.airlines.setOnClickListener {
            clickListener.onItemClicked(scheduleModel)
        }
        itemView.airlines.text = scheduleModel.flightModels[0].airlineModel.name
        itemView.price.text = "$${Random(100).nextInt(1001)}"
    }

    companion object {
        fun create(
            @LayoutRes layoutID: Int, parent: ViewGroup,
            clickListener: BindableItemClickListener<ScheduleModel>,
            viewPool: RecyclerView.RecycledViewPool
        ): ScheduleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(layoutID, parent, false)
            return ScheduleViewHolder(view, clickListener, viewPool)
        }
    }
}