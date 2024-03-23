package com.linoop.quickcart.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.size.Precision
import com.linoop.quickcart.ui.theme.LightGray
import com.linoop.quickcart.utils.Constants.USER_AGENT
import com.linoop.quickcart.utils.Constants.USER_AGENT_VALUE
import okhttp3.Interceptor
import okhttp3.OkHttpClient

@Composable
fun CoilImageLoader(
    imageUrl: Any,
    contentDesc: String,
    modifier: Modifier,
    @DrawableRes placeholder: Int,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center
) {
    val painter = getCoilAsyncPainter(imageUrl = imageUrl, placeholder = placeholder)
    Box(contentAlignment = Alignment.Center) {
        Image(
            painter = painter,
            contentDescription = contentDesc,
            modifier = modifier,
            contentScale = contentScale,
            alignment = alignment
        )
        ImageLoadingProgress(painter = painter)
    }
}

@Composable
private fun getCoilAsyncPainter(imageUrl: Any, placeholder: Int): AsyncImagePainter =
    rememberAsyncImagePainter(
        model = imageUrl,
        imageLoader = ImageLoader
            .Builder(LocalContext.current)
            .precision(Precision.EXACT)
            .components { add(SvgDecoder.Factory()) }
            .okHttpClient {
                OkHttpClient.Builder().addInterceptor { chain: Interceptor.Chain ->
                    val request = chain.request()
                        .newBuilder()
                        .addHeader(USER_AGENT, USER_AGENT_VALUE)
                        .build()
                    chain.proceed(request)
                }.build()
            }
            .respectCacheHeaders(false)
            .build(),
        error = painterResource(id = placeholder),
        contentScale = ContentScale.Fit
    )

@Composable
private fun ImageLoadingProgress(painter: AsyncImagePainter) {
    var isLoading by remember { mutableStateOf(true) }
    isLoading = when (painter.state) {
        is AsyncImagePainter.State.Loading -> true
        is AsyncImagePainter.State.Empty -> false
        is AsyncImagePainter.State.Error -> false
        is AsyncImagePainter.State.Success -> false
    }
    if (isLoading) CircularProgressIndicator(color = LightGray)
}
