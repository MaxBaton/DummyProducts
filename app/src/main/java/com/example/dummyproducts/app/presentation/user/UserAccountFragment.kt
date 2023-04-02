package com.example.dummyproducts.app.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.dummyproducts.app.presentation.user.viewModel.UserViewModel
import com.example.dummyproducts.databinding.FragmentUserAccountBinding

class UserAccountFragment: Fragment() {
    private var binding: FragmentUserAccountBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserAccountBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding ?: return) {
            userViewModel.liveDataUser.observe(requireActivity()) { user ->
                textViewFirstname.text = user.firstName
                textViewLastname.text = user.lastName

                Glide
                    .with(requireContext())
                    .load(user.image)
                    .into(imageViewAvatar)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }
}