package com.linoop.quickcart.product.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.linoop.quickcart.R
import com.linoop.quickcart.model.Product
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
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Text(text = "$${product.price}", style = MaterialTheme.typography.headlineLarge)
            RatingBar(rating = product.rating)
            Text(text = product.description, style = MaterialTheme.typography.bodySmall)
            Text(text = product.category, style = MaterialTheme.typography.bodySmall)
            Text(text = product.brand, style = MaterialTheme.typography.bodySmall)
            Text(text = "Stock ${product.price}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview
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