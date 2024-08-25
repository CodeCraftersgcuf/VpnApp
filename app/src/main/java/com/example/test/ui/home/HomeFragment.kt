package com.example.test.ui.home

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.test.R
import com.example.test.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val connectButton: Button = binding.connectButton
        val countrySelector: LinearLayout = binding.countrySelector
        connectButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D3D3D3"))

        connectButton.setOnClickListener {
//            it.setBackgroundColor(Color.GREEN)
//            it.animate().rotation(360f).setDuration(1000).start()
        }

        countrySelector.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_countryListFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
