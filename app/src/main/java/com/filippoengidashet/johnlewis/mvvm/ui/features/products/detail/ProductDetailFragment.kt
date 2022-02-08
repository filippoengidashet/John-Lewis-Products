package com.filippoengidashet.johnlewis.mvvm.ui.features.products.detail

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import com.filippoengidashet.johnlewis.R
import com.filippoengidashet.johnlewis.common.ImageLoader
import com.filippoengidashet.johnlewis.mvvm.data.model.ProductUiState
import com.filippoengidashet.johnlewis.mvvm.ui.base.BaseFragment
import com.filippoengidashet.johnlewis.mvvm.ui.widgets.DotIndicatorView
import com.filippoengidashet.johnlewis.mvvm.ui.widgets.ProductSpecListView
import com.filippoengidashet.johnlewis.mvvm.vm.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 16:00.
 */
@AndroidEntryPoint
class ProductDetailFragment : BaseFragment(R.layout.fragment_product_detail) {

    private val viewModel: ProductDetailViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressView = view.findViewById<ProgressBar>(R.id.progress_bar)
        val pager = view.findViewById<ViewPager>(R.id.pager)
        val indicatorView = view.findViewById<DotIndicatorView>(R.id.indicator_view)

        val textPrice = view.findViewById<TextView>(R.id.text_price)
        val textOffers = view.findViewById<TextView>(R.id.text_offers)
        val textIncludedServices = view.findViewById<TextView>(R.id.text_included_services)
        val textProductCode = view.findViewById<TextView>(R.id.text_product_code)
        val textProductInfo = view.findViewById<TextView>(R.id.text_product_info)
        val productsSpecsView = view.findViewById<ProductSpecListView>(R.id.product_specs)

        val pagerAdapter = ImagePagerAdapter(imageLoader)
        pager.adapter = pagerAdapter

        viewModel.getUiState().observe(this) { state ->
            when (state) {
                ProductUiState.Loading -> {
                    progressView.visibility = View.VISIBLE
                }
                is ProductUiState.Success -> {
                    progressView.visibility = View.GONE
                    val product = state.product

                    pagerAdapter.setItems(product.media)
                    indicatorView.setViewPager(pager)

                    textPrice.text = getString(R.string.text_price_format, product.price)

                    with(textOffers) {
                        product.displaySpecialOffer.takeIf { it.isNotEmpty() }?.let {
                            text = it
                        } ?: kotlin.run {
                            visibility = View.GONE
                        }
                    }

                    with(textIncludedServices) {
                        product.additionalServices.takeIf { it.isNotEmpty() }?.let {
                            text = it
                        } ?: kotlin.run {
                            visibility = View.GONE
                        }
                    }

                    textProductInfo.text = HtmlCompat.fromHtml(
                        product.productInformation, HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                    textProductCode.text = getString(R.string.text_product_code, product.code)

                    productsSpecsView.setSpecs(product.productSpecs)
                }
                is ProductUiState.Error -> {
                    progressView.visibility = View.GONE
                    showToast(state.message)
                }
            }
        }
        val productId = requireArguments().getString(ARGS_PRODUCT_ID)

        productId?.let { id ->
            viewModel.getProductDetail(id)
        }
    }

    companion object {

        const val ARGS_PRODUCT_ID = "args.product.id"
    }
}
