package kz.ticketon.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MapsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private var param1: LatLng? = null
    private var param2: LatLng? = null
    lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
            param2 = it.getParcelable(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: LatLng, param2: LatLng) =
            MapsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                    putParcelable(ARG_PARAM2, param2)
                }
            }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val markerOptions = MarkerOptions()
        val markerOptions2 = MarkerOptions()
        markerOptions.position(param1!!)
        markerOptions2.position(param2!!)
        val firstMarker = mMap.addMarker(markerOptions)
        val secondMarker = mMap.addMarker(markerOptions2)




        val rectOptions = PolylineOptions()
            .add(
                param1!!, param2!!
            )
        mMap.addPolyline(rectOptions)

                val builder = LatLngBounds.Builder()
                builder.include(param1!!).include(param2!!)

                val location: CameraUpdate =
                    CameraUpdateFactory.newLatLngBounds(builder.build(), 45)
                mMap?.animateCamera(location)

    }


}