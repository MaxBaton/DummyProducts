package com.example.dummyproducts.app.presentation.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.dummyproducts.R
import com.example.dummyproducts.app.presentation.user.viewModel.UserViewModel
import com.example.dummyproducts.app.presentation.user.viewModel.UserViewModelFactory
import com.example.dummyproducts.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels { UserViewModelFactory(context = applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel.liveDataUser.observe(this) { user ->

        }

        with(binding) {

        }
    }
}