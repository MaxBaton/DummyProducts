package com.example.dummyproducts.app.presentation.products

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dummyproducts.R
import com.example.dummyproducts.app.presentation.common.createWaitAlertDialog
import com.example.dummyproducts.app.presentation.common.showShortToast
import com.example.dummyproducts.app.presentation.products.logic.ActionAllProductsMenuMode
import com.example.dummyproducts.app.presentation.products.viewmodel.ProductViewModel
import com.example.dummyproducts.app.presentation.user.viewModel.UserViewModel
import com.example.dummyproducts.databinding.FragmentAllProductsBinding
import com.example.dummyproducts.databinding.ProductAllItemBinding
import com.example.dummyproducts.domain.products.models.Product
import com.example.dummyproducts.domain.products.models.ProductWithCheck
import com.example.dummyproducts.domain.products.usecase.GetPriceStr
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.viewbinding.BindableItem

class AllProductsFragment: Fragment() {
    private var binding: FragmentAllProductsBinding? = null
    private val productViewModel: ProductViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    // For Recycler
    private val groupieAdapter = GroupieAdapter()
    private val section = Section()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAllProductsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding ?: return) {
            productViewModel.getProductsWithCheck(
                token = userViewModel.liveDataUser.value?.token ?: "",
                onSuccess = {
                    progressBar.visibility = View.GONE
                },
                onError = {
                    progressBar.visibility = View.GONE
                    textView.text = "Ошибка зазгрузки продуктов"
                }
            )

            productViewModel.productsWithCheckLiveData.observe(viewLifecycleOwner) { productsWithCheck ->
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_all_products, menu)

        productViewModel.actionMenuModeLiveData.observe(viewLifecycleOwner) { mode ->
            val isAddDelMode = mode == ActionAllProductsMenuMode.ADD_MODE

            menu.findItem(R.id.action_add_mode).isVisible = isAddDelMode
            menu.findItem(R.id.action_confirm).isVisible = !isAddDelMode
            menu.findItem(R.id.action_cancel).isVisible = !isAddDelMode

            changeCheckBoxRecyclerVisibility(isVisibleCheckBox = !isAddDelMode)
        }
    }

    private fun changeCheckBoxRecyclerVisibility(isVisibleCheckBox: Boolean) {
        for (i in 0 until section.itemCount) {
            val item = section.getItem(i)
            val productItem = (item as ProductItem)
            productItem.isVisibleCheckBox = isVisibleCheckBox
            section.notifyItemChanged(i)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add_mode -> {
                productViewModel.changeActionMenuMode(menuMode = ActionAllProductsMenuMode.CONFIRM_CANCEL_MODE)
            }
            R.id.action_cancel -> {
                productViewModel.changeActionMenuMode(menuMode = ActionAllProductsMenuMode.ADD_MODE)
            }
            R.id.action_confirm -> {
                val dialogWait = requireContext().createWaitAlertDialog(
                    title = "Добавление",
                    message = "Добавление продуктов"
                )
                dialogWait.show()

                val selectedProducts = getSelectedProducts()
                productViewModel.addProducts(
                    products = selectedProducts,
                    onSuccess = {
                        dialogWait.dismiss()
                        requireContext().showShortToast(text = "Продукты успешно добавлены")
                        productViewModel.changeActionMenuMode(menuMode = ActionAllProductsMenuMode.ADD_MODE)
                    },
                    onError = {
                        dialogWait.dismiss()
                        requireContext().showShortToast(text = "Ошибка добавления продуктов")
                    }
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getSelectedProducts(): List<Product> {
        val listProduct = mutableListOf<Product>()

        for (i in 0 until section.itemCount) {
            val productItem = section.getItem(i) as ProductItem
            val isCheck = productItem.productWithCheck.isInUserList
            if (isCheck) {
                listProduct.add(productItem.productWithCheck.product)
            }
        }
        return listProduct
    }

    inner class ProductItem(
        val productWithCheck: ProductWithCheck,
        var isVisibleCheckBox: Boolean = false
    ): BindableItem<ProductAllItemBinding>() {
        override fun bind(viewBinding: ProductAllItemBinding, position: Int) {
            with(viewBinding) {
                checkBoxIsInUserList.visibility = if (isVisibleCheckBox) View.VISIBLE else View.GONE
                checkBoxIsInUserList.isChecked = productWithCheck.isInUserList
                checkBoxIsInUserList.setOnCheckedChangeListener { buttonView, isChecked ->
                    productWithCheck.isInUserList = isChecked
                }

                textViewTitle.text = productWithCheck.product.title
                textViewPrice.text = GetPriceStr(price = productWithCheck.product.price).get()
                textViewRating.text = productWithCheck.product.rating.toString()

                Glide
                    .with(requireContext())
                    .load(productWithCheck.product.thumbnail)
                    .into(imageViewThumbnail)
            }
        }

        override fun getLayout() = R.layout.product_all_item

        override fun initializeViewBinding(view: View) = ProductAllItemBinding.bind(view)

    }
}