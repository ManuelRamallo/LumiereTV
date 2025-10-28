package com.mramallo.lumieretv.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mramallo.lumieretv.databinding.FragmentSearchBinding

class SearchFragment: Fragment() {

    private lateinit var _binding: FragmentSearchBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return _binding.root
    }

}