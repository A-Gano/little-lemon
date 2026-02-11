package com.example.littlelemon

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImage
import coil.request.ImageRequest

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        homeViewModel.loadMenuItems()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header with Logo and Profile
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.raw.logo),
                    contentDescription = "Little Lemon Logo",
                    modifier = Modifier
                        .height(50.dp)
                        .width(150.dp)
                )

                Image(
                    painter = painterResource(id = R.raw.profile),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(45.dp)
                        .clip(RoundedCornerShape(22.5.dp))
                        .clickable { navController.navigate(Profile.route) }
                )
            }
        }

        // Hero Section - Dark Green Background
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF495E57))
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Little Lemon",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF4CE14),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "Chicago",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 16.dp),
                        color = Color.White,
                        fontSize = 15.sp,
                        lineHeight = 20.sp
                    )

                    Image(
                        painter = painterResource(id = R.raw.heroimage),
                        contentDescription = "Hero Image",
                        modifier = Modifier
                            .size(130.dp, 140.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        // Search Bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF495E57))
                    .padding(bottom = 24.dp)
            ) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { homeViewModel.updateSearchQuery(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    placeholder = {
                        Text(
                            text = "Enter search phrase",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray
                        )
                    },


                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        }

        // Order for Delivery Title
        item {
            Text(
                text = "ORDER FOR DELIVERY!",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(start = 16.dp, top = 16.dp, bottom = 12.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF495E57),
                letterSpacing = 1.sp
            )
        }

        // Category Chips - Exactly as in the image
        if (uiState.categories.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(start = 16.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Starters Chip
                    CategoryChip(
                        category = "Starters",
                        isSelected = uiState.selectedCategory == "starters",
                        onClick = { homeViewModel.selectCategory("starters") }
                    )

                    // Mains Chip
                    CategoryChip(
                        category = "Mains",
                        isSelected = uiState.selectedCategory == "mains",
                        onClick = { homeViewModel.selectCategory("mains") }
                    )

                    // Desserts Chip
                    CategoryChip(
                        category = "Desserts",
                        isSelected = uiState.selectedCategory == "desserts",
                        onClick = { homeViewModel.selectCategory("desserts") }
                    )

                    // Drinks Chip - Only show if exists in menu
                    if (uiState.categories.contains("drinks")) {
                        CategoryChip(
                            category = "Drinks",
                            isSelected = uiState.selectedCategory == "drinks",
                            onClick = { homeViewModel.selectCategory("drinks") }
                        )
                    }
                }
            }
        }

        // Menu Items Section
        if (uiState.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF495E57))
                }
            }
        } else if (uiState.error != null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Error loading menu",
                            color = Color.Red,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Try Again",
                            modifier = Modifier
                                .clickable { homeViewModel.refreshMenu() }
                                .background(Color(0xFF495E57), RoundedCornerShape(8.dp))
                                .padding(horizontal = 24.dp, vertical = 10.dp),
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        } else if (uiState.filteredMenuItems.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (uiState.menuItems.isEmpty())
                            "No menu items available"
                        else
                            "No items match your search",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }
        } else {
            items(
                items = uiState.filteredMenuItems,
                key = { it.id }
            ) { menuItem ->
                MenuItemCard(
                    menuItem = menuItem,
                    onItemClick = {
                        // Navigate to detail screen if needed
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryChip(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isSelected) Color(0xFF495E57) else Color(0xFFE0E0E0)
            )
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 10.dp)
    ) {
        Text(
            text = category,
            color = if (isSelected) Color.White else Color.Black,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun MenuItemCard(
    menuItem: MenuItem,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onItemClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = menuItem.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = menuItem.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$${menuItem.price}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF495E57)
                )
            }

            // Food Image from Server
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(menuItem.image)
                    .crossfade(true)
                    .build(),
                contentDescription = menuItem.title,
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_launcher_foreground),
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground)
            )
        }
    }
}

private fun RowScope.AsyncImage(
    model: Any,
    contentDescription: String,
    modifier: Modifier,
    contentScale: ContentScale,
    error: Painter,
    placeholder: Painter
) {
}

