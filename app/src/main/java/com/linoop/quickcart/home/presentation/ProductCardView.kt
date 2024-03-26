package com.linoop.quickcart.home.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.linoop.quickcart.R
import com.linoop.quickcart.main.model.Product
import com.linoop.quickcart.ui.theme.White
import com.linoop.quickcart.ui.theme.GrayShade1
import com.linoop.quickcart.utils.onItemClick
import com.linoop.quickcart.widgets.CoilImageLoader
import com.linoop.quickcart.widgets.RatingBar

@Composable
fun DrawProductCardView(product: Product, onClick: onItemClick) {
    Surface(
        shadowElevation = dimensionResource(id = R.dimen.elevation_medium),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_medium),
                vertical = dimensionResource(id = R.dimen.padding_tiny)
            ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_large)),
    ) {
        Row(modifier = Modifier
            .background(if (isSystemInDarkTheme()) GrayShade1 else White)
            .fillMaxWidth()
            .clickable {
                onClick.invoke(product.productId!!)
            }) {
            CoilImageLoader(
                imageUrl = product.thumbnail,
                contentDesc = product.title,
                modifier = Modifier.size(dimensionResource(id = R.dimen.box_size_medium)),
                placeholder = R.drawable.baseline_broken_image_24,
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium))
            ) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall
                )
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                    Text(text = "$${product.price}", style = MaterialTheme.typography.headlineSmall)
                    Text(
                        text = "(${product.discountPercentage}% Off)",
                        modifier = Modifier.padding(
                            dimensionResource(id = R.dimen.padding_small)
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                RatingBar(rating = product.rating, starSize= dimensionResource(id = R.dimen.rating_bar_size_medium))
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true
)
@Composable
private fun DrawCardViewPreview() {
    DrawProductCardView(
        product = Product(
            brand = "Apple",
            category = "Phone",
            title = "iPhone 15 Plus"
        )
    ) {}
}