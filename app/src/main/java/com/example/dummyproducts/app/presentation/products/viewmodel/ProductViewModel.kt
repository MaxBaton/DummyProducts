package com.example.dummyproducts.app.presentation.products.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.usecase.GetAllProducts
import com.example.dummyproducts.domain.products.usecase.GetUserProducts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getAllProducts: GetAllProducts,
    private val getUserProducts: GetUserProducts
): ViewModel() {
    private val mutableProductsLiveData = MutableLiveData<List<Product>>()
    val productsLiveData = mutableProductsLiveData

    fun getAllProducts(onSuccess: () -> Unit, onError: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val products = getAllProducts.get()
            CoroutineScope(Dispatchers.Main).launch {
                products?.let { mutableProductsLiveData.value = it }
                if (products != null && products.isNotEmpty()) {
                    onSuccess()
                }else {
                    onError()
                }
            }
        }
    }

    fun getUserProducts(onSuccess: () -> Unit, onError: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val products = getUserProducts.get()
            CoroutineScope(Dispatchers.Main).launch {
                products?.let { mutableProductsLiveData.value = it }
                if (products != null && products.isNotEmpty()) {
                    onSuccess()
                }else {
                    onError()
                }
            }
        }
    }
}