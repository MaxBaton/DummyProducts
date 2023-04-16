package com.example.dummyproducts.app.presentation.products.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dummyproducts.app.presentation.products.logic.ActionAllProductsMenuMode
import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.models.ProductWithCheck
import com.example.dummyproducts.domain.products.usecase.AddProducts
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
    private val getUserProducts: GetUserProducts,
    private val addProducts: AddProducts
): ViewModel() {
    // AllProducts
    private val mutableAllProductsLiveData = MutableLiveData<List<Product>>()
    val productsAllLiveData = mutableAllProductsLiveData
    // UserProducts
    private val mutableUserProductsLiveData = MutableLiveData<List<Product>>()
    val productsUserLiveData = mutableUserProductsLiveData
    // ProductWithCheck
    private val mutableProductsWithCheckLiveData = MutableLiveData<List<ProductWithCheck>>()
    val productsWithCheckLiveData = mutableProductsWithCheckLiveData
    // MenuActionMode
    private val mutableActionAllProductsMenuModeLiveData = MutableLiveData<ActionAllProductsMenuMode>()
    val actionMenuModeLiveData = mutableActionAllProductsMenuModeLiveData

    init {
        mutableActionAllProductsMenuModeLiveData.value = ActionAllProductsMenuMode.ADD_MODE
    }

    fun getAllProducts(onSuccess: () -> Unit, onError: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val products = getAllProducts.get()
            CoroutineScope(Dispatchers.Main).launch {
                products?.let { mutableAllProductsLiveData.value = it }
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
                products?.let { mutableUserProductsLiveData.value = it }
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

    fun changeActionMenuMode(menuMode: ActionAllProductsMenuMode) {
        mutableActionAllProductsMenuModeLiveData.value = menuMode
    }

    fun addProducts(products: List<Product>, onSuccess: () -> Unit, onError: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val isAdd = addProducts.add(products = products)
            CoroutineScope(Dispatchers.Main).launch {
                if (isAdd) {
                    onSuccess()
                }else {
                    onError()
                }
            }
        }
    }
}