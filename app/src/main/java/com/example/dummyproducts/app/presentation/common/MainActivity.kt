package com.example.dummyproducts.app.presentation.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.dummyproducts.R
import com.example.dummyproducts.app.presentation.products.viewmodel.ProductViewModel
import com.example.dummyproducts.app.presentation.products.viewmodel.ProductViewModelFactory
import com.example.dummyproducts.app.presentation.user.UserAccountFragment
import com.example.dummyproducts.app.presentation.user.viewModel.UserViewModel
import com.example.dummyproducts.app.presentation.user.viewModel.UserViewModelFactory
import com.example.dummyproducts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels { UserViewModelFactory(context = applicationContext) }
    private val productViewModel: ProductViewModel by viewModels { ProductViewModelFactory(context = applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        productViewModel.productsLiveData.observe(this) {}

        userViewModel.liveDataUser.observe(this) {}
    }
}