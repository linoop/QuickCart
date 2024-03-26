package com.linoop.quickcart.home.converter

import com.linoop.quickcart.home.viewmodel.HomeViewModel
import com.linoop.quickcart.home.state.HomePageUserEvent
import com.linoop.quickcart.home.state.HomePageUserState
import com.linoop.quickcart.utils.Converter

/**
 * HomeViewModel converter
 * Converts HomeViewModel to home page User sate [HomePageUserState]
 */
class HomeViewModelToUserState : Converter<HomeViewModel, HomePageUserState> {
    override fun convert(input: HomeViewModel) = with(input) {
        HomePageUserState(productPagingItems = productState)
    }
}

/**
 * HomeViewModel converter
 * Converts HomeViewModel to home page User event [HomePageUserEvent]
 */
class HomeViewModelToUserEvent : Converter<HomeViewModel, HomePageUserEvent> {
    override fun convert(input: HomeViewModel) = with(input) {
        HomePageUserEvent(getProductList = { getProductsList() })
    }
}