package com.example.dummyproducts.app.presentation.products

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dummyproducts.R
import com.example.dummyproducts.app.presentation.common.createWaitAlertDialog
import com.example.dummyproducts.app.presentation.common.showShortToast
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
    // For selected
    private var isSelectedMode = false
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

            productViewModel.productsUserLiveData.observe(viewLifecycleOwner) { products ->
                val productsItem = mutableListOf<ProductUserItem>()
                products.forEach {
                    productsItem.add(ProductUserItem(product = it))
                }
                section.update(productsItem)
            }

            productViewModel.selectedUserProductsLiveData.observe(viewLifecycleOwner) { products ->
                if (products.isEmpty()) {
                    isSelectedMode = false
                    showHideAllMenuItems(isVisible = false)
                }else {
                    isSelectedMode = true
                    showHideAllMenuItems(isVisible = true)
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

        groupieAdapter.setOnItemLongClickListener { item, view ->
            if (!isSelectedMode) {
                isSelectedMode = true

                val position = section.getPosition(item)
                val productUserItem = item as ProductUserItem
                productUserItem.isSelected = !productUserItem.isSelected
                section.notifyItemChanged(position)
                productViewModel.addSelectedProduct(product = productUserItem.product)
            }

            true
        }

        groupieAdapter.setOnItemClickListener { item, view ->
            if (isSelectedMode) {
                val position = section.getPosition(item)
                val productUserItem = item as ProductUserItem

                if (productUserItem.isSelected) {
                    productViewModel.deleteSelectedProduct(product = productUserItem.product)
                }else {
                    productViewModel.addSelectedProduct(product = productUserItem.product)
                }

                productUserItem.isSelected = !productUserItem.isSelected
                section.notifyItemChanged(position)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_user_products, menu)
        this.menu = menu
        showHideAllMenuItems(isVisible = false)
    }

    private fun unSelectAllProducts() {
        for (i in 0 until section.itemCount) {
            val item = section.getItem(i) as ProductUserItem
            if (item.isSelected) {
                item.isSelected = false
                section.notifyItemChanged(i)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_cancel -> {
                unSelectAllProducts()
                productViewModel.deleteAllSelectedProduct()
            }
            R.id.action_del_mode -> {
                val waitAlertDialog = requireContext().createWaitAlertDialog(
                    title = "Удаление",
                    message = "Удаление продуктов"
                )
                if (!waitAlertDialog.isShowing) {
                    waitAlertDialog.show()
                }

                val products = getSelectedProduct()
                productViewModel.deleteProducts(
                    products = products,
                    onSuccess = {
                        productViewModel.deleteAllSelectedProduct()
                        waitAlertDialog.dismiss()
                        requireContext().showShortToast(text = "Успешное удалеие")
                    },
                    onError = {
                        waitAlertDialog.dismiss()
                        requireContext().showShortToast(text = "Ошибка удаления")
                    }
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getSelectedProduct(): List<Product> {
        val products = mutableListOf<Product>()

        for (i in 0 until section.itemCount) {
            val productUserItem = section.getItem(i) as ProductUserItem
            if (productUserItem.isSelected) {
                products.add(productUserItem.product)
            }
        }

        return products
    }

    private fun showHideAllMenuItems(isVisible: Boolean) {
        menu?.let {
            for (i in 0 until it.size()) {
                it.getItem(i).isVisible = isVisible
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    inner class ProductUserItem(
        val product: Product,
        var isSelected: Boolean = false
    ): BindableItem<ProductUserItemBinding>() {
        override fun bind(viewBinding: ProductUserItemBinding, position: Int) {
            with(viewBinding) {
                val color = if (isSelected) {
                    ContextCompat.getColor(requireContext(), R.color.selected_user_product)
                }else {
                    ContextCompat.getColor(requireContext(), R.color.white)
                }
                layoutProductUserItem.setBackgroundColor(color)

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