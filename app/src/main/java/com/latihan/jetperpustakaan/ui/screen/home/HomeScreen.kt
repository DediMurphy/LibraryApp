package com.latihan.jetperpustakaan.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import com.latihan.jetperpustakaan.ViewModelFactory
import com.latihan.jetperpustakaan.di.Injection
import com.latihan.jetperpustakaan.model.OrderBooks
import com.latihan.jetperpustakaan.ui.common.UiState
import com.latihan.jetperpustakaan.ui.component.ListBook

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllBooks()
            }
            is UiState.Success -> {
                HomeContent(
                    orderBooks = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    orderBooks: List<OrderBooks>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier.testTag("RewardList")
    ) {
        items(orderBooks) { data ->
            ListBook(
                photoUrl = data.reward.photoUrl,
                name = data.reward.nameBook,
                modifier = modifier
                    .clickable {
                        navigateToDetail(data.reward.id)
                    }
            )
        }
    }
}
