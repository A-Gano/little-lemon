package com.example.littlelemon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemRow(item: MenuItem) {

    Row(Modifier.padding(10.dp)) {

        Column(Modifier.weight(1f)) {
            Text(item.title)
            Text(item.description)
            Text("$${item.price}")
        }

        GlideImage(
            model = item.image,
            contentDescription = item.title,
            modifier = Modifier.size(80.dp)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun ShowMeniItemRow(){
    val item = MenuItem(1,"Greek","Good food",2.1,"https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true","salad")
    MenuItemRow(item)
}