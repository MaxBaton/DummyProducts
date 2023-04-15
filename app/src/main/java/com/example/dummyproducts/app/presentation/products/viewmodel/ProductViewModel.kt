package com.example.dummyproducts.app.presentation.products.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.models.ProductWithCheck
import com.example.dummyproducts.domain.products.usecase.GetAllProducts
import com.example.dummyproducts.domain.products.usecase.GetProductsWithCheck
import com.example.dummyproducts.domain.products.usecase.GetUserProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getAllProducts: GetAllProducts,
    private val getUserProducts: GetUserProducts
): ViewModel() {
    // Product
    private val mutableProductsLiveData = MutableLiveData<List<Product>>()
    val productsLiveData = mutableProductsLiveData
    // ProductWithCheck
    private val mutableProductsWithCheckLiveData = MutableLiveData<List<ProductWithCheck>>()
    val productsWithCheckLiveData = mutableProductsWithCheckLiveData

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
                if (products != null) {
                    onSuccess()
                }else {
                    onError()
                }
            }
        }
    }

    fun getProductsWithCheck(onSuccess: () -> Unit, onError: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val allProductsAsync = async { getAllProducts.get() }
            val userProductsAsync = async { getUserProducts.get() }
            val allProducts = allProductsAsync.await()
            val userProducts = userProductsAsync.await()

            CoroutineScope(Dispatchers.Main).launch {
                if (allProducts == null || userProducts == null) {
                    onError()
                }else {
                    val getProductsWithCheck = GetProductsWithCheck(
                        allProducts = allProducts,
                        userProducts = userProducts
                    )
                    val productsWithCheck = getProductsWithCheck.get()
                    mutableProductsWithCheckLiveData.value = productsWithCheck
                    onSuccess()
                }
            }
        }
    }
}