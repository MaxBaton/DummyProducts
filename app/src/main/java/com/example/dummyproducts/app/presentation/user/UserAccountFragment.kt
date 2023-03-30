package com.example.dummyproducts.app.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dummyproducts.databinding.FragmentUserAccountBinding

class UserAccountFragment: Fragment() {
    private var binding: FragmentUserAccountBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserAccountBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding ?: return) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }
}