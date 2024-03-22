package com.linoop.quickcart.transformation

import com.linoop.quickcart.viewmodel.HomeViewModel
import com.linoop.quickcart.state.HomePageUserEvent
import com.linoop.quickcart.state.HomePageUserState
import com.linoop.quickcart.utils.Transformer

class HomeViewModelToUserState : Transformer<HomeViewModel, HomePageUserState> {
    override fun transform(input: HomeViewModel) = with(input) {
        HomePageUserState(
            queryError = queryErrorState,
            dataState = apiDataState.value,
            productPagingItems = productState
        )
    }
}

class HomeViewModelToUserEvent : Transformer<HomeViewModel, HomePageUserEvent> {
    override fun transform(input: HomeViewModel) = with(input) {
        HomePageUserEvent(
            getProduct = { getProducts() },
            getProductList = {getProductsList()}
        )
    }
}