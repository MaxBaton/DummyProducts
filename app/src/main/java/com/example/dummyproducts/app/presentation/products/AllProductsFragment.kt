package com.example.dummyproducts.app.presentation.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dummyproducts.R
import com.example.dummyproducts.app.presentation.products.viewmodel.ProductViewModel
import com.example.dummyproducts.databinding.FragmentAllProductsBinding
import com.example.dummyproducts.databinding.ProductAllFragmentItemBinding
import com.example.dummyproducts.domain.products.models.ProductWithCheck
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.viewbinding.BindableItem

class AllProductsFragment: Fragment() {
    private var binding: FragmentAllProductsBinding? = null
    private val productViewModel: ProductViewModel by activityViewModels()
    private val groupieAdapter = GroupieAdapter()
    private val section = Section()

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
                },
                onError = {
                    progressBar.visibility = View.GONE
                    textView.text = "Ошибка зазгрузки продуктов"
                }
            )

            productViewModel.productsWithCheckLiveData.observe(requireActivity()) { productsWithCheck ->
                productsWithCheck.forEach {
                    section.add(ProductItem(productWithCheck = it))
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

    inner class ProductItem(private val productWithCheck: ProductWithCheck): BindableItem<ProductAllFragmentItemBinding>() {
        override fun bind(viewBinding: ProductAllFragmentItemBinding, position: Int) {
            with(viewBinding) {
                checkBoxIsInUserList.isChecked = productWithCheck.isInUserList
                textViewTitle.text = productWithCheck.product.title
                textViewPrice.text = productWithCheck.product.price.toString()
                textViewRating.text = productWithCheck.product.rating.toString()

                Glide
                    .with(requireContext())
                    .load(productWithCheck.product.thumbnail)
                    .into(imageViewThumbnail)
            }
        }

        override fun getLayout() = R.layout.product_all_fragment_item

        override fun initializeViewBinding(view: View) = ProductAllFragmentItemBinding.bind(view)

    }
}