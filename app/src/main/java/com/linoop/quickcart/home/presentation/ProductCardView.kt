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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linoop.quickcart.R
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.ui.theme.Black
import com.linoop.quickcart.ui.theme.White
import com.linoop.quickcart.ui.theme.Brown
import com.linoop.quickcart.utils.onItemClick
import com.linoop.quickcart.widgets.CoilImageLoader

@Composable
fun DrawProductCardView(product: Product, onClick: onItemClick) {
    Surface(
        shadowElevation = dimensionResource(id = R.dimen.elevation_medium),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding_large),
                vertical = dimensionResource(id = R.dimen.padding_small)
            ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_large)),
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke(product.productId!!)
            }) {
            CoilImageLoader(
                imageUrl = product.thumbnail,
                contentDesc = product.title,
                modifier = Modifier.size(100.dp),
                placeholder = R.drawable.baseline_broken_image_24
            )
            Column(
                modifier = Modifier
                    .background(if (isSystemInDarkTheme()) Black else Brown)
                    .padding(dimensionResource(id = R.dimen.padding_large)),
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSystemInDarkTheme()) White else Black
                )
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isSystemInDarkTheme()) White else Black
                )
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