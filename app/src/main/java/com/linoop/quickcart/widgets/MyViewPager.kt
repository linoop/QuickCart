package com.linoop.quickcart.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.linoop.quickcart.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageViewPager(modifier: Modifier, imageUrls: List<String>) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { imageUrls.size })
    Box {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            Box(
                modifier = modifier
            ) {
                CoilImageLoader(
                    imageUrl = imageUrls[page],
                    contentDesc = stringResource(id = R.string.view_pager_image),
                    modifier = Modifier.fillMaxSize(),
                    placeholder = R.drawable.baseline_broken_image_24,
                    contentScale = ContentScale.Fit
                )
            }
        }
        LazyRow(modifier = Modifier.align(Alignment.BottomCenter)) {
            items(imageUrls.size) {
                RadioButton(
                    selected = it == pagerState.currentPage,
                    onClick = { scope.launch { pagerState.scrollToPage(it) } }
                )
            }
        }
    }

}