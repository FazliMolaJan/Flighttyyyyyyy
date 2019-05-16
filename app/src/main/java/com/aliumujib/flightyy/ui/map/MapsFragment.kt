package com.aliumujib.flightyy.ui.map


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aliumujib.flightyy.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.CameraUpdate
import android.graphics.Color
import com.aliumujib.flightyy.data.remote.models.Flight
import com.aliumujib.flightyy.presentation.models.schedule.FlightModel
import com.aliumujib.flightyy.ui.utils.ext.delayForASecond
import com.google.android.gms.maps.model.*
import java.util.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import androidx.databinding.adapters.ViewBindingAdapter.setPadding
import android.widget.LinearLayout
import android.widget.TextView
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.presentation.models.AirportModel
import com.aliumujib.flightyy.presentation.models.schedule.ArrivalDepartureModel
import com.google.maps.android.ui.IconGenerator


class MapsFragment : Fragment(), OnMapReadyCallback {


    private var map: GoogleMap? = null
    val builder = LatLngBounds.Builder()

    override fun onMapReady(p0: GoogleMap?) {
        this.map = p0

        val scheduleModel = MapsFragmentArgs.fromBundle(arguments!!).schedule

        delayForASecond {
            map?.apply {
                uiSettings.isZoomControlsEnabled = true

                scheduleModel.flightModels.forEach {
                    addPolyLineForFlight(it)
                }

                val bounds = builder.build()
                val padding = 150 // offset from edges of the map in pixels
                val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                this.moveCamera(cu)
                this.animateCamera(cu)
            }
        }

    }


    private fun makeCoolMarker(time: String, flight: AirportModel): MarkerOptions? {
        val iconFactory_right = IconGenerator(context)
        iconFactory_right.setBackground(null)

        val markerView = View.inflate(activity, com.aliumujib.flightyy.R.layout.custom_info_window, null)
        iconFactory_right.setContentView(markerView)

        val markerTime = markerView.findViewById<TextView>(R.id.time)
        val placeName = markerView.findViewById<TextView>(R.id.place_name)

        markerTime.text = time
        placeName.text = flight.code

        var markerOptions = MarkerOptions()

        return markerOptions.position(flight.latlng()).anchor(
            0.5f,
            0.5f
        ).icon(BitmapDescriptorFactory.fromBitmap(iconFactory_right.makeIcon(flight.carriers)))
    }

    private fun addPolyLineForFlight(flight: FlightModel) {

        map?.let {
            val marker1 =
                it.addMarker(makeCoolMarker(flight.departureModel.formattedTime, flight.departureModel.airportModel))
            val marker2 =
                it.addMarker(makeCoolMarker(flight.arrivalModel.formattedTime, flight.arrivalModel.airportModel))

            val pattern = Arrays.asList(Dash(30f), Gap(10f))
            val popt = PolylineOptions().add(flight.arrivalModel.airportModel.latlng())
                .add(flight.departureModel.airportModel.latlng())
                .width(5f).color(Color.BLACK).pattern(pattern)
                .geodesic(true)

            builder.include(marker1.position)
            builder.include(marker2.position)

            it.addPolyline(popt)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

}
