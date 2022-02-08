package com.filippoengidashet.johnlewis.mvvm.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.filippoengidashet.johnlewis.mvvm.data.model.ApiResult
import com.filippoengidashet.johnlewis.mvvm.data.model.ProductsUiState
import com.filippoengidashet.johnlewis.mvvm.data.model.entity.ProductEntity
import com.filippoengidashet.johnlewis.mvvm.data.repository.ProductGridRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 13:48.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
class ProductGridViewModelTest {

    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: ProductGridRepository

    private lateinit var viewModel: ProductGridViewModel

    @Before
    fun setUp() {
        this.viewModel = ProductGridViewModel(repository)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `test load products success`() {
        val products = mutableListOf<ProductEntity>()
        val result = ApiResult.Success(products)

        runBlockingTest {
            `when`(repository.getProductsResult(anyString())).thenReturn(result)
            viewModel.getUiState().observeForever { uiStates ->

                val expected = listOf(
                    ProductsUiState.Loading,
                    ProductsUiState.Success(products)
                )
                assertThat(uiStates, `is`(expected))
            }
        }
        viewModel.loadProducts("")
    }

    @Test
    fun `test load products failure`() {
        val error = Throwable("Unexpected Error!")
        val result = ApiResult.Error(error)

        runBlockingTest {
            `when`(repository.getProductsResult(anyString())).thenReturn(result)
            viewModel.getUiState().observeForever { uiStates ->

                val expected = listOf(
                    ProductsUiState.Loading,
                    ProductsUiState.Error(error.message)
                )
                assertThat(uiStates, `is`(expected))
            }
        }
        viewModel.loadProducts("")
    }

    @After
    fun tearDown() {
        //clear references
        Dispatchers.resetMain()
    }
}
