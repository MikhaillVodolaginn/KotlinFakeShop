package com.example.kotlinFakeShop.feature_products.presentation.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.kotlinFakeShop.R
import com.example.kotlinFakeShop.core.presentation.ui.theme.Light
import com.example.kotlinFakeShop.core.presentation.ui.theme.PrimaryYellow
import com.example.kotlinFakeShop.core.util.LoadingAnimation
import com.example.kotlinFakeShop.destinations.ProductDetailsScreenDestination
import com.example.kotlinFakeShop.feature_products.domain.model.Product
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalComposeUiApi::class)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var filtersExpanded by remember { mutableStateOf(false) }
    val productsState = viewModel.productsState.value
    val categories = viewModel.categoriesState.value

    Scaffold(
        topBar = {
            MyTopAppBar(
                currentSearchText = viewModel.searchTerm.value,
                onSearchTextChange = {
                    viewModel.setSearchTerm(it)
                },
                onSearch = {
                    keyboardController?.hide()
                    viewModel.getProducts(
                        searchTerm = viewModel.searchTerm.value
                    )
                },
                onToggleExpand = {
                    filtersExpanded = !filtersExpanded
                },
            )
        },
    ) {
        HomeScreenContent(
            categories = categories,
            productsState = productsState,
            navigator = navigator,
            bannerImageUrl = viewModel.bannerImageState.value,
            selectedCategory = viewModel.selectedCategory.value,
            onSelectCategory = { category ->
                viewModel.setCategory(category)
                viewModel.getProducts(viewModel.selectedCategory.value)
            }
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun HomeScreenContent(
    categories: List<String>,
    productsState: ProductsState,
    navigator: DestinationsNavigator,
    bannerImageUrl: String,
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                Card(
                    elevation = 0.dp,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current)
                                .data(data = bannerImageUrl)
                                .apply(block = fun ImageRequest.Builder.() {
                                    placeholder(R.drawable.ic_placeholder)
                                    crossfade(true)
                                }).build()
                        ),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Black Friday Banner"
                    )
                }

            } // Header for some banner Image

            // Some spacer
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item(span = { GridItemSpan(2) }) {
                Categories(
                    categories = categories,
                    onSelectCategory = onSelectCategory,
                    selectedCategory = selectedCategory
                )
            } // Header with a lazyRow for product categories

            // Some spacer
            item(span = { GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Actual product items list
            items(productsState.products) { product ->
                ProductItem(
                    product = product,
                    navigator = navigator,
                    modifier = Modifier
                        .width(150.dp)
                )
            }
        }

        if (productsState.isLoading) {
            LoadingAnimation(
                modifier = Modifier.align(Center),
                circleSize = 16.dp,
            )
        }

        if (productsState.error != null) Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Center)
                .padding(16.dp),
            text = productsState.error,
            color = Color.Red
        )
    }
}

@Composable
private fun ProductItem(
    product: Product,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable {
                navigator.navigate(ProductDetailsScreenDestination(product))
            },
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(data = product.image)
                        .apply(block = fun ImageRequest.Builder.() {
                            placeholder(R.drawable.ic_placeholder)
                            crossfade(true)
                        }).build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .align(CenterHorizontally),
                contentScale = ContentScale.Inside
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.category,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraLight
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top,
            ) {
                Icon(
                    modifier = Modifier
                        .size(18.dp)
                        .align(CenterVertically),
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = null,
                    tint = PrimaryYellow
                )
                Text(
                    modifier = Modifier.align(CenterVertically),
                    text = "${product.rating.rate} (${product.rating.count})",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$${product.price}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .size(40.dp)
                    .align(End),
                shape = CircleShape,
                border = BorderStroke(0.dp, Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White,
                    backgroundColor = PrimaryYellow
                )
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Filled.AddShoppingCart,
                    contentDescription = null,
                    tint = Light
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

    }
}

@Composable
fun MyTopAppBar(
    currentSearchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    onToggleExpand: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = "https://firebasestorage.googleapis.com/v0/b/mealtime-7a501.appspot.com/o/tinywow_Joomia%20Black%20Friday_16608968%20(1).png?alt=media&token=8b874def-e543-482e-80f7-c8cbe9d9f206")
                            .apply(block = fun ImageRequest.Builder.() {
                                crossfade(true)
                            }).build()
                    ),
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(35.dp),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Привет, Johnd", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun Categories(
    categories: List<String>,
    onSelectCategory: (String) -> Unit,
    selectedCategory: String,
) {
    LazyRow(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(categories) { category ->
            Text(
                text = category,
                style = typography.body1.merge(),
                color = Color.Black,
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(
                            size = 8.dp,
                        ),
                    )
                    .clickable {
                        onSelectCategory(category)
                    }
                    .background(
                        if (category == selectedCategory) {
                            PrimaryYellow
                        } else {
                            Light
                        }
                    )
                    .padding(
                        10.dp
                    )
            )
        }
    }
}