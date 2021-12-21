package com.example.foodtracking.view.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.foodtracking.R

class MacroFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_macro, container, false)
        val txtMacro = view.findViewById<TextView>(R.id.txtMacro)
        val microText = arguments!!.getString("microText")
        txtMacro.text = microText
        return view
    }
}