package com.example.dummyproducts.app.presentation.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dummyproducts.app.presentation.products.viewmodel.ProductViewModel
import com.example.dummyproducts.databinding.FragmentAllProductsBinding

class AllProductsFragment: Fragment() {
    private var binding: FragmentAllProductsBinding? = null
    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAllProductsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding ?: return) {
            productViewModel.getAllProducts(
                onSuccess = {
                    progressBar.visibility = View.GONE
                    textView.text = "Продукты загружены"
                },
                onError = {
                    progressBar.visibility = View.GONE
                    textView.text = "Ошибка загрузки продуктов"
                }
            )

            productViewModel.productsLiveData.observe(requireActivity()) { products ->

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}