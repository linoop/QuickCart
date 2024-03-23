package com.linoop.quickcart.home.transformation

import com.linoop.quickcart.home.viewmodel.HomeViewModel
import com.linoop.quickcart.home.state.HomePageUserEvent
import com.linoop.quickcart.home.state.HomePageUserState
import com.linoop.quickcart.utils.Transformer

class HomeViewModelToUserState : Transformer<HomeViewModel, HomePageUserState> {
    override fun transform(input: HomeViewModel) = with(input) {
        HomePageUserState(
            queryError = queryErrorState,
            productPagingItems = productState
        )
    }
}

class HomeViewModelToUserEvent : Transformer<HomeViewModel, HomePageUserEvent> {
    override fun transform(input: HomeViewModel) = with(input) {
        HomePageUserEvent(
            getProductList = { getProductsList() }
        )
    }
}