package com.filippoengidashet.johnlewis.mvvm.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippoengidashet.johnlewis.mvvm.data.model.ApiResult
import com.filippoengidashet.johnlewis.mvvm.data.model.ProductsUiState
import com.filippoengidashet.johnlewis.mvvm.data.repository.ProductGridRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 13:43.
 */
@HiltViewModel
class ProductGridViewModel @Inject constructor(
    private val repository: ProductGridRepository,
) : ViewModel() {

    private val _uiState = MutableLiveData<ProductsUiState>()
    fun getUiState(): LiveData<ProductsUiState> = _uiState

    private var loaded: Boolean = false

    fun loadProducts(keyword: String, refresh: Boolean = false) {
        if (!refresh && loaded) return
        viewModelScope.launch {
            _uiState.value = ProductsUiState.Loading
            val result = repository.getProductsResult(keyword)
            _uiState.value = when (result) {
                is ApiResult.Error -> ProductsUiState.Error(result.error.message)
                is ApiResult.Success -> {
                    loaded = true
                    ProductsUiState.Success(result.data)
                }
            }
        }
    }
}
