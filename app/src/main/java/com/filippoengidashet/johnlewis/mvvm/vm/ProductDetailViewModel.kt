package com.filippoengidashet.johnlewis.mvvm.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippoengidashet.johnlewis.mvvm.data.model.ApiResult
import com.filippoengidashet.johnlewis.mvvm.data.model.ProductUiState
import com.filippoengidashet.johnlewis.mvvm.data.repository.ProductDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 13:47.
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductDetailRepository,
) : ViewModel() {

    private val _uiState = MutableLiveData<ProductUiState>()
    fun getUiState(): LiveData<ProductUiState> = _uiState

    private var loaded: Boolean = false

    fun getProductDetail(productId: String, refresh: Boolean = false) {
        if (!refresh && loaded) return
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            val result = repository.getProductResult(productId)
            _uiState.value = when (result) {
                is ApiResult.Error -> ProductUiState.Error(result.error.message)
                is ApiResult.Success -> {
                    loaded = true
                    ProductUiState.Success(result.data)
                }
            }
        }
    }
}
