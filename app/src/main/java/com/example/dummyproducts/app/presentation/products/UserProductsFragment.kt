package com.example.dummyproducts.app.presentation.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dummyproducts.R
import com.example.dummyproducts.app.presentation.products.viewmodel.ProductViewModel
import com.example.dummyproducts.databinding.FragmentUserProductsBinding
import com.example.dummyproducts.databinding.ProductUserItemBinding
import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.usecase.GetPriceStr
import com.xwray.groupie.viewbinding.BindableItem

class UserProductsFragment: Fragment() {
    private var binding: FragmentUserProductsBinding? = null
    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserProductsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding ?: return) {
            productViewModel.getUserProducts(
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

    inner class ProductUserItem(private val product: Product): BindableItem<ProductUserItemBinding>() {
        override fun bind(viewBinding: ProductUserItemBinding, position: Int) {
            with(viewBinding) {
                textViewTitle.text = product.title
                textViewPrice.text = GetPriceStr(price = product.price).get()
                textViewRating.text = product.rating.toString()
            }
        }

        override fun getLayout() = R.layout.product_user_item

        override fun initializeViewBinding(view: View) = ProductUserItemBinding.bind(view)

    }
}