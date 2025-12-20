package com.example.mvvm.ui

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.mvvm.BuildConfig
import com.example.mvvm.HomeViewModel
import com.example.mvvm.HomeViewModel.HomeScreenState.EmptyState
import com.example.mvvm.HomeViewModel.HomeScreenState.ErrorState
import com.example.mvvm.HomeViewModel.HomeScreenState.LoadingState
import com.example.mvvm.HomeViewModel.HomeScreenState.SuccessState
import com.example.mvvm.data.Row
import com.example.mvvm.ui.theme.White


@Composable
fun HomeScreen(
    uiState: HomeViewModel.HomeScreenState,
    innerPadding: PaddingValues,
) {
    when (uiState) {
        is EmptyState -> {}
        is ErrorState -> {}
        is SuccessState -> HomeScreenSuccess(uiState.homeList)
        is LoadingState -> {}
    }
}

@Composable
fun HomeScreenSuccess(rows: List<Row>) {
    LazyColumn {
        items(rows.filter { it.titleList.isNotEmpty() }) { row ->
            Row(row)
        }
    }
}

@Composable
fun Row(row: Row) {
    val listState = rememberLazyListState()
    // Define the snap fling behavior using the list state
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    Column(modifier = Modifier.padding(start = 8.dp)) {
        Text(
            text = row.header,
            color = White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp),
        )
        LazyRow(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(row.titleList) { rowTitle ->
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    modifier =
                        Modifier
                            .width(100.dp)
                            .height(143.dp)
                            .clip(RoundedCornerShape(4.dp)),
                    model = BuildConfig.POSTER_URL + rowTitle.imageUrl,
                    contentDescription = rowTitle.title,
                )
            }
        }
    }
}
