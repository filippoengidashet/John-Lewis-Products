package com.filippoengidashet.johnlewis.mvvm.ui.features.products.grid

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.filippoengidashet.johnlewis.R
import com.filippoengidashet.johnlewis.common.ImageLoader
import com.filippoengidashet.johnlewis.mvvm.data.model.ProductsUiState
import com.filippoengidashet.johnlewis.mvvm.ui.base.BaseFragment
import com.filippoengidashet.johnlewis.mvvm.ui.features.products.detail.ProductDetailFragment
import com.filippoengidashet.johnlewis.utils.Utils
import com.filippoengidashet.johnlewis.mvvm.vm.ProductGridViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 12:00.
 */
@AndroidEntryPoint
class ProductGridFragment : BaseFragment(R.layout.fragment_product_grid) {

    private val viewModel: ProductGridViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridCount = resources.getInteger(R.integer.grid_count)

        val listAdapter = ProductListAdapter(layoutInflater, imageLoader) { prod ->
            findNavController().navigate(
                R.id.action_productGridFragment_to_productDetailFragment, Bundle().apply {
                    putString("title", prod.title)
                    putString(ProductDetailFragment.ARGS_PRODUCT_ID, prod.productId)
                }
            )
        }

        val vDecorator = DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
        val hDecorator = DividerItemDecoration(view.context, DividerItemDecoration.HORIZONTAL)

        val progressView = view.findViewById<ProgressBar>(R.id.progress_bar)
        val searchInput = view.findViewById<EditText>(R.id.search_input).apply {
            setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val keyword = v.text.toString()
                    viewModel.loadProducts(keyword, true)
                }
                return@setOnEditorActionListener false
            }
        }

        view.findViewById<ImageView>(R.id.button_search).setOnClickListener {
            Utils.hideKeyboard(requireContext(), searchInput)
            val keyword = searchInput.text.toString()
            viewModel.loadProducts(keyword, true)
        }

        view.findViewById<RecyclerView>(R.id.rv_list).apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), gridCount)
            addItemDecoration(vDecorator)
            addItemDecoration(hDecorator)
            adapter = listAdapter
        }

        viewModel.getUiState().observe(this) { state ->
            when (state) {
                ProductsUiState.Loading -> {
                    progressView.visibility = View.VISIBLE
                }
                is ProductsUiState.Success -> {
                    progressView.visibility = View.GONE
                    listAdapter.setItems(state.products)
                }
                is ProductsUiState.Error -> {
                    progressView.visibility = View.GONE
                    showErrorDialog(state.message)
                }
            }
        }
        viewModel.loadProducts("dishwasher")
    }

    private fun showErrorDialog(message: String?) {
        val dialog = AlertDialog.Builder(requireActivity())
            .setTitle("Failed to Load")
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { d, _ ->
                d.dismiss()
            }
            .create()
        dialog.show()
    }
}
