package com.example.dummyproducts.app.presentation.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.dummyproducts.R
import com.example.dummyproducts.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val controller by lazy { findNavController(viewId = R.id.fragment_container_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBarWithNavController(navController = controller)
    }

    override fun onSupportNavigateUp(): Boolean {
        return controller.navigateUp() or super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        controller.currentDestination?.let { destination ->
            if (destination.id == R.id.userAccountFragment) {
                this.finish()
                return
            }
        }
        super.onBackPressed()
    }
}