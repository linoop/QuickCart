package com.linoop.quickcart.home.converter

import com.linoop.quickcart.home.viewmodel.HomeViewModel
import com.linoop.quickcart.home.state.HomePageUserEvent
import com.linoop.quickcart.home.state.HomePageUserState
import com.linoop.quickcart.utils.Converter

class HomeViewModelToUserState : Converter<HomeViewModel, HomePageUserState> {
    override fun convert(input: HomeViewModel) = with(input) {
        HomePageUserState(productPagingItems = productState)
    }
}

class HomeViewModelToUserEvent : Converter<HomeViewModel, HomePageUserEvent> {
    override fun convert(input: HomeViewModel) = with(input) {
        HomePageUserEvent(getProductList = { getProductsList() })
    }
}