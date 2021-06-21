package kz.ticketon.test

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.gms.maps.model.LatLng


class SearchFragment : Fragment() {

    private lateinit var firstPoint: AutoCompleteTextView
    private lateinit var secondPoint: AutoCompleteTextView
    private lateinit var button: Button
    private var firstLatLng: LatLng? = null
    private var secondLatLng: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstPoint = view.findViewById(R.id.firstPoint)
        secondPoint = view.findViewById(R.id.secondPoint)
        button = view.findViewById(R.id.button)

        firstPoint.addTextChangedListener(textWatcher1)
        secondPoint.addTextChangedListener(textWatcher2)
        button.setOnClickListener {
            when{
                firstLatLng==null || firstPoint.text.toString().isEmpty()->{
                    firstPoint.error = "Заполните это поле!"
                }
                secondLatLng==null || secondPoint.text.toString().isEmpty()->{
                    secondPoint.error = "Заполните это поле!"
                }
                else->{
                    activity?.supportFragmentManager!!
                        .beginTransaction()
                        .addToBackStack(this::class.java.simpleName)
                        .replace(R.id.frame_main, MapsFragment.newInstance(firstLatLng!!,secondLatLng!!), MapsFragment::class.java.simpleName)
                        .commit()
                }
            }
        }

    }

    private val textWatcher1 = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            s?.let {
                searchLocation1(it.toString())
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }

    private val textWatcher2 = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            s?.let {
                searchLocation2(it.toString())
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    }

    private fun searchResult1(list: MutableList<Address>){
        var nameList: MutableList<String> = mutableListOf()

        for (i in list.indices) {
            nameList.add(list[i].getAddressLine(i))
        }

        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            nameList.toTypedArray()
        )

        adapter.notifyDataSetChanged()
        firstPoint.setAdapter(adapter)

        firstPoint.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, id ->
//                doctorId = idList[position]
                firstLatLng= LatLng(list[position].latitude,list[position].longitude)
            }

        firstPoint.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    firstPoint.showDropDown()
                }
            }
    }

    private fun searchResult2(list: MutableList<Address>){
        var nameList: MutableList<String> = mutableListOf()

        for (i in list.indices) {
            nameList.add(list[i].getAddressLine(i))
        }

        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            nameList.toTypedArray()
        )

        adapter.notifyDataSetChanged()
        secondPoint.setAdapter(adapter)

        secondPoint.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, id ->
//                doctorId = idList[position]
                secondLatLng= LatLng(list[position].latitude,list[position].longitude)
            }

        secondPoint.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    secondPoint.showDropDown()
                }
            }
    }

    fun searchLocation1(text: String) {
        var addressList: MutableList<Address> = mutableListOf()
        if (text != "") {
            val geocoder = Geocoder(requireContext())
            try {
                addressList = geocoder.getFromLocationName(text, 15)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if(addressList.isNotEmpty()){  try {
                searchResult1(addressList)
            }catch (e: Exception){
                e.printStackTrace()
            }
            }
        }
    }

    fun searchLocation2(text: String) {
        var addressList: MutableList<Address> = mutableListOf()
        if (text != "") {
            val geocoder = Geocoder(requireContext())
            try {
                addressList = geocoder.getFromLocationName(text, 15)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if(addressList.isNotEmpty()){
                try {
                    searchResult2(addressList)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }


    companion object {
    }
}