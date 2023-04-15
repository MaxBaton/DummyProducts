package com.example.dummyproducts.app.presentation.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dummyproducts.R
import com.example.dummyproducts.app.presentation.products.viewmodel.ProductViewModel
import com.example.dummyproducts.databinding.FragmentUserProductsBinding
import com.example.dummyproducts.databinding.ProductUserItemBinding
import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.usecase.GetPriceStr
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.viewbinding.BindableItem

class UserProductsFragment: Fragment() {
    private var binding: FragmentUserProductsBinding? = null
    private val productViewModel: ProductViewModel by activityViewModels()
    // For Recycler
    private val groupieAdapter = GroupieAdapter()
    private val section = Section()

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
                },
                onError = {
                    progressBar.visibility = View.GONE
                    textView.text = "Ошибка загрузки продуктов"
                }
            )

            productViewModel.productsLiveData.observe(requireActivity()) { products ->
                products.forEach {
                    section.add(ProductUserItem(product = it))
                }
            }

            section.apply {
//                setHideWhenEmpty()
            }

            groupieAdapter.add(section)

            recyclerViewProducts.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = groupieAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
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

                Glide
                    .with(requireContext())
                    .load(product.thumbnail)
                    .into(imageViewThumbnail)
            }
        }

        override fun getLayout() = R.layout.product_user_item

        override fun initializeViewBinding(view: View) = ProductUserItemBinding.bind(view)

    }
}