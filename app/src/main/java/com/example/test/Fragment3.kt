package com.example.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment

class Fragment3 : Fragment() {

    // Declare layouts and icons for each country dropdown
    private lateinit var firstCountryLayout: LinearLayout
    private lateinit var dropdownLayout1: LinearLayout
    private lateinit var dropdownIcon1: ImageView

    private lateinit var secondCountryLayout: LinearLayout
    private lateinit var dropdownLayout2: LinearLayout
    private lateinit var dropdownIcon2: ImageView

    private lateinit var thirdCountryLayout: LinearLayout
    private lateinit var dropdownLayout3: LinearLayout
    private lateinit var dropdownIcon3: ImageView

    private lateinit var fourthCountryLayout: LinearLayout
    private lateinit var dropdownLayout4: LinearLayout
    private lateinit var dropdownIcon4: ImageView

    private lateinit var fifthCountryLayout: LinearLayout
    private lateinit var dropdownLayout5: LinearLayout
    private lateinit var dropdownIcon5: ImageView

    private lateinit var sixthCountryLayout: LinearLayout
    private lateinit var dropdownLayout6: LinearLayout
    private lateinit var dropdownIcon6: ImageView

    private lateinit var seventhCountryLayout: LinearLayout
    private lateinit var dropdownLayout7: LinearLayout
    private lateinit var dropdownIcon7: ImageView

    private lateinit var eighthCountryLayout: LinearLayout
    private lateinit var dropdownLayout8: LinearLayout
    private lateinit var dropdownIcon8: ImageView

    private lateinit var ninthCountryLayout: LinearLayout
    private lateinit var dropdownLayout9: LinearLayout
    private lateinit var dropdownIcon9: ImageView

    private lateinit var tenthCountryLayout: LinearLayout
    private lateinit var dropdownLayout10: LinearLayout
    private lateinit var dropdownIcon10: ImageView

    private lateinit var elevenCountryLayout: LinearLayout
    private lateinit var dropdownLayout11: LinearLayout
    private lateinit var dropdownIcon11: ImageView

    private lateinit var twelthCountryLayout: LinearLayout
    private lateinit var dropdownLayout12: LinearLayout
    private lateinit var dropdownIcon12: ImageView

    // State variables to track visibility of each dropdown
    private var isDropdownVisible1 = false
    private var isDropdownVisible2 = false
    private var isDropdownVisible3 = false
    private var isDropdownVisible4 = false
    private var isDropdownVisible5 = false
    private var isDropdownVisible6 = false
    private var isDropdownVisible7 = false
    private var isDropdownVisible8 = false
    private var isDropdownVisible9=false
    private var isDropdownVisible10=false
    private var isDropdownVisible11=false
    private var isDropdownVisible12=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fragment1, container, false)

        // Initialize views for the first country
        firstCountryLayout = view.findViewById(R.id.first_country)
        dropdownLayout1 = view.findViewById(R.id.dropdown_layout1)
        dropdownIcon1 = view.findViewById(R.id.dropdownIcon1)

        // Initialize views for the second country
        secondCountryLayout = view.findViewById(R.id.second_country)
        dropdownLayout2 = view.findViewById(R.id.dropdown_layout2)
        dropdownIcon2 = view.findViewById(R.id.dropdownIcon2)

        // Initialize views for the third country
        thirdCountryLayout = view.findViewById(R.id.third_country)
        dropdownLayout3 = view.findViewById(R.id.dropdown_layout3)
        dropdownIcon3 = view.findViewById(R.id.dropdownIcon3)

        // Initialize views for the fourth country
        fourthCountryLayout = view.findViewById(R.id.fourth_country)
        dropdownLayout4 = view.findViewById(R.id.dropdown_layout4)
        dropdownIcon4 = view.findViewById(R.id.dropdownIcon4)

        // Initialize views for the fifth country
        fifthCountryLayout = view.findViewById(R.id.fifth_country)
        dropdownLayout5 = view.findViewById(R.id.dropdown_layout5)
        dropdownIcon5 = view.findViewById(R.id.dropdownIcon5)

        // Initialize views for the sixth country
        sixthCountryLayout = view.findViewById(R.id.sixth_country)
        dropdownLayout6 = view.findViewById(R.id.dropdown_layout6)
        dropdownIcon6 = view.findViewById(R.id.dropdownIcon6)

        // Initialize views for the seventh country
        seventhCountryLayout = view.findViewById(R.id.seventh_country)
        dropdownLayout7 = view.findViewById(R.id.dropdown_layout7)
        dropdownIcon7 = view.findViewById(R.id.dropdownIcon7)

        // Initialize views for the eighth country
        eighthCountryLayout = view.findViewById(R.id.eighth_country)
        dropdownLayout8 = view.findViewById(R.id.dropdown_layout8)
        dropdownIcon8 = view.findViewById(R.id.dropdownIcon8)

        // Initialize views for the ninth country
        ninthCountryLayout = view.findViewById(R.id.ninth_country)
        dropdownLayout9 = view.findViewById(R.id.dropdown_layout9)
        dropdownIcon9 = view.findViewById(R.id.dropdownIcon9)

        // Initialize views for the tenth country
        tenthCountryLayout = view.findViewById(R.id.tenth_country)
        dropdownLayout10 = view.findViewById(R.id.dropdown_layout10)
        dropdownIcon10 = view.findViewById(R.id.dropdownIcon10)

        // Initialize views for the eleven country
        elevenCountryLayout = view.findViewById(R.id.eleven_country)
        dropdownLayout11 = view.findViewById(R.id.dropdown_layout11)
        dropdownIcon11 = view.findViewById(R.id.dropdownIcon11)

        // Initialize views for the twelth country
        twelthCountryLayout = view.findViewById(R.id.twelth_country)
        dropdownLayout12 = view.findViewById(R.id.dropdown_layout12)
        dropdownIcon12 = view.findViewById(R.id.dropdownIcon12)
        // Set click listeners to toggle dropdown visibility for each country
        firstCountryLayout.setOnClickListener {
            toggleDropdown1()
        }

        secondCountryLayout.setOnClickListener {
            toggleDropdown2()
        }

        thirdCountryLayout.setOnClickListener {
            toggleDropdown3()
        }

        fourthCountryLayout.setOnClickListener {
            toggleDropdown4()
        }

        fifthCountryLayout.setOnClickListener {
            toggleDropdown5()
        }

        sixthCountryLayout.setOnClickListener {
            toggleDropdown6()
        }
        seventhCountryLayout.setOnClickListener {
            toggleDropdown7()
        }

        eighthCountryLayout.setOnClickListener {
            toggleDropdown8()
        }
        ninthCountryLayout.setOnClickListener {
            toggleDropdown9()
        }
        tenthCountryLayout.setOnClickListener {
            toggleDropdown10()
        }
        elevenCountryLayout.setOnClickListener {
            toggleDropdown11()
        }
        twelthCountryLayout.setOnClickListener {
            toggleDropdown12()
        }
        return view
    }

    private fun toggleDropdown1() {
        if (isDropdownVisible1) {
            // Hide the dropdown
            dropdownLayout1.visibility = View.GONE
            dropdownIcon1.rotation = 0f  // Reset the dropdown icon rotation
        } else {
            // Show the dropdown
            dropdownLayout1.visibility = View.VISIBLE
            dropdownIcon1.rotation = 180f  // Rotate the dropdown icon
        }
        isDropdownVisible1 = !isDropdownVisible1
    }

    private fun toggleDropdown2() {
        if (isDropdownVisible2) {
            // Hide the dropdown
            dropdownLayout2.visibility = View.GONE
            dropdownIcon2.rotation = 0f  // Reset the dropdown icon rotation
        } else {
            // Show the dropdown
            dropdownLayout2.visibility = View.VISIBLE
            dropdownIcon2.rotation = 180f  // Rotate the dropdown icon
        }
        isDropdownVisible2 = !isDropdownVisible2
    }

    private fun toggleDropdown3() {
        if (isDropdownVisible3) {
            // Hide the dropdown
            dropdownLayout3.visibility = View.GONE
            dropdownIcon3.rotation = 0f  // Reset the dropdown icon rotation
        } else {
            // Show the dropdown
            dropdownLayout3.visibility = View.VISIBLE
            dropdownIcon3.rotation = 180f  // Rotate the dropdown icon
        }
        isDropdownVisible3 = !isDropdownVisible3
    }

    private fun toggleDropdown4() {
        if (isDropdownVisible4) {
            // Hide the dropdown
            dropdownLayout4.visibility = View.GONE
            dropdownIcon4.rotation = 0f  // Reset the dropdown icon rotation
        } else {
            // Show the dropdown
            dropdownLayout4.visibility = View.VISIBLE
            dropdownIcon4.rotation = 180f  // Rotate the dropdown icon
        }
        isDropdownVisible4 = !isDropdownVisible4
    }

    private fun toggleDropdown5() {
        if (isDropdownVisible5) {
            // Hide the dropdown
            dropdownLayout5.visibility = View.GONE
            dropdownIcon5.rotation = 0f  // Reset the dropdown icon rotation
        } else {
            // Show the dropdown
            dropdownLayout5.visibility = View.VISIBLE
            dropdownIcon5.rotation = 180f  // Rotate the dropdown icon
        }
        isDropdownVisible5 = !isDropdownVisible5
    }

    private fun toggleDropdown6() {
        if (isDropdownVisible6) {
            // Hide the dropdown
            dropdownLayout6.visibility = View.GONE
            dropdownIcon6.rotation = 0f  // Reset the dropdown icon rotation
        } else {
            // Show the dropdown
            dropdownLayout6.visibility = View.VISIBLE
            dropdownIcon6.rotation = 180f  // Rotate the dropdown icon
        }
        isDropdownVisible6 = !isDropdownVisible6
    }

    private fun toggleDropdown7() {
        if (isDropdownVisible7) {
            // Hide the dropdown
            dropdownLayout7.visibility = View.GONE
            dropdownIcon7.rotation = 0f  // Reset the dropdown icon rotation
        } else {
            // Show the dropdown
            dropdownLayout7.visibility = View.VISIBLE
            dropdownIcon7.rotation = 180f  // Rotate the dropdown icon
        }
        isDropdownVisible7 = !isDropdownVisible7
    }

    private fun toggleDropdown8() {
        if (isDropdownVisible8) {
            // Hide the dropdown
            dropdownLayout8.visibility = View.GONE
            dropdownIcon8.rotation = 0f  // Reset the dropdown icon rotation
        } else {
            // Show the dropdown
            dropdownLayout8.visibility = View.VISIBLE
            dropdownIcon8.rotation = 180f  // Rotate the dropdown icon
        }
        isDropdownVisible8 = !isDropdownVisible8
    }
    private fun toggleDropdown9() {
        if (isDropdownVisible9) {
            // Hide the dropdown
            dropdownLayout9.visibility = View.GONE
            dropdownIcon9.rotation = 0f  // Reset the dropdown icon rotation
        } else {
            // Show the dropdown
            dropdownLayout9.visibility = View.VISIBLE
            dropdownIcon9.rotation = 180f  // Rotate the dropdown icon
        }
        isDropdownVisible9 = !isDropdownVisible9
    }
    private fun toggleDropdown10() {
        if (isDropdownVisible10) {
            // Hide the dropdown
            dropdownLayout10.visibility = View.GONE
            dropdownIcon10.rotation = 0f  // Reset the dropdown icon rotation
        } else {
            // Show the dropdown
            dropdownLayout10.visibility = View.VISIBLE
            dropdownIcon10.rotation = 180f  // Rotate the dropdown icon
        }
        isDropdownVisible10 = !isDropdownVisible10
    }
    private fun toggleDropdown11() {
        if (isDropdownVisible11) {
            // Hide the dropdown
            dropdownLayout11.visibility = View.GONE
            dropdownIcon11.rotation = 0f  // Reset the dropdown icon rotation
        } else {
            // Show the dropdown
            dropdownLayout11.visibility = View.VISIBLE
            dropdownIcon11.rotation = 180f  // Rotate the dropdown icon
        }
        isDropdownVisible11 = !isDropdownVisible11
    }
    private fun toggleDropdown12() {
        if (isDropdownVisible12) {
            // Hide the dropdown
            dropdownLayout12.visibility = View.GONE
            dropdownIcon12.rotation = 0f  // Reset the dropdown icon rotation
        } else {
            // Show the dropdown
            dropdownLayout12.visibility = View.VISIBLE
            dropdownIcon12.rotation = 180f  // Rotate the dropdown icon
        }
        isDropdownVisible12 = !isDropdownVisible12
    }
}

