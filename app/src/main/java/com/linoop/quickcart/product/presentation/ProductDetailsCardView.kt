package com.linoop.quickcart.product.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.linoop.quickcart.R
import com.linoop.quickcart.model.Product
import com.linoop.quickcart.ui.theme.GrayShade1
import com.linoop.quickcart.ui.theme.White
import com.linoop.quickcart.widgets.RatingBar

@Composable
fun DrawProductDetailsCardView(product: Product) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_medium)),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius_medium)),
        shadowElevation = dimensionResource(id = R.dimen.elevation_medium)
    ) {
        Column(
            modifier = Modifier
                .background(if (isSystemInDarkTheme()) GrayShade1 else White)
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = product.brand,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = product.category, style = MaterialTheme.typography.bodySmall)
            }

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                Text(text = "$${product.price}", style = MaterialTheme.typography.headlineLarge)
                Text(
                    text = "(${product.discountPercentage}% Off)",
                    modifier = Modifier.padding(
                        dimensionResource(id = R.dimen.padding_small)
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            RatingBar(rating = product.rating)
            Text(text = product.description, style = MaterialTheme.typography.bodySmall)
            Text(text = "Stock: ${product.price}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun DrawProductDetailsCardViewPreview() {
    DrawProductDetailsCardView(
        Product(
            brand = "Apple",
            category = "Phone",
            description = "Apple IPhone 14 Plus",
            discountPercentage = 12.0f,
            price = 5983,
            rating = 2.2f,
            stock = 45,
            title = "IPhone 14 Plus"
        )
    )
}