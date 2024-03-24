package com.linoop.quickcart

import android.content.Context
import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.linoop.quickcart.network.ApiService
import com.linoop.quickcart.utils.ApiState
import com.linoop.quickcart.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.mockStatic
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
open class BaseTest {


    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRole = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    protected lateinit var context: Context

    protected lateinit var apiService: ApiServiceTest

    @Before
    @CallSuper
    open fun setUp() {
        context = mock(Context::class.java)
        apiService = ApiServiceTest()
    }


}

@Suppress("UNCHECKED_CAST")
class ApiServiceTest(
    var apiStateToTest: ApiState = ApiState.Success,
    var testException: Boolean = false
) : ApiServiceImplTest() {
    suspend fun <T> execute(apiCall: suspend () -> Response<T>): Resource<T> {
        if (testException) throw RuntimeException()
        return when (apiStateToTest) {
            ApiState.Loading, ApiState.Initial -> Resource.Loading()
            ApiState.Success -> Resource.Success(apiCall.invoke().body() as T, "Success")
            ApiState.Error -> Resource.Error(apiCall.invoke().body() as T, "Api Error")
        }
    }
}