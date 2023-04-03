package com.example.dummyproducts.app.presentation.products.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.models.Products
import com.example.dummyproducts.domain.products.usecase.GetProducts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getProducts: GetProducts
): ViewModel() {
    private val mutableProductsLiveData = MutableLiveData<List<Product>>()
    val productsLiveData = mutableProductsLiveData

    fun getAllProducts(onSuccess: () -> Unit, onError: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val products = getProducts.getAllProducts()
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