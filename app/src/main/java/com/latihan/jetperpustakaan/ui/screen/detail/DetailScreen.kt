package com.latihan.jetperpustakaan.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.latihan.jetperpustakaan.R
import com.latihan.jetperpustakaan.ViewModelFactory
import com.latihan.jetperpustakaan.di.Injection
import com.latihan.jetperpustakaan.ui.common.UiState
import com.latihan.jetperpustakaan.ui.theme.JetPerpustakaanTheme


@Composable
fun DetailScreen(
    bookId: Long,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToSave: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getBooksId(bookId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    image = data.reward.photoUrl,
                    nameBook = data.reward.nameBook,
                    author = data.reward.author,
                    releaseDate = data.reward.releaseDate,
                    description = data.reward.description,
                    onBackClick = navigateBack,
                    navigateToSave = { _ ->
                        viewModel.getBooksId(data.reward.id)
                        navigateToSave()
                    },
                    modifier = modifier

                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    @DrawableRes image: Int,
    nameBook: String,
    author: String,
    releaseDate: String,
    description: String,
    onBackClick: () -> Unit,
    navigateToSave: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
          Fab(navigateToSave = navigateToSave)
        }

    ) {innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Box() {
                    Image(
                        painter = painterResource(image),
                        contentDescription = null,
                        alignment = Alignment.Center,
                        modifier = modifier
                            .height(300.dp)
                            .fillMaxSize()
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onBackClick() }
                    )
                }
                Column(modifier = modifier.padding(8.dp)) {
                    Text(
                        text = nameBook,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Text(
                        text = author,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Text(
                        text = releaseDate,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                    Spacer(modifier = modifier.height(10.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun Fab(
    navigateToSave: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val isAddIcon = remember { mutableStateOf(true) }

    FloatingActionButton(
        onClick = { isAddIcon.value = !isAddIcon.value },
        modifier = modifier
    ) {
        Icon(
            if (isAddIcon.value) Icons.Filled.FavoriteBorder else Icons.Filled.Favorite,
            contentDescription = if (isAddIcon.value) "Add" else "Done",
        )
    }
}

@Preview
@Composable
fun DetailScreen() {
    JetPerpustakaanTheme {
        DetailContent(
            R.drawable.atomic_habits,
            nameBook = "Atomis ",
            author = "james",
            description = "jdiwjia",
            releaseDate = "2002",
            onBackClick = {},
            navigateToSave = {}
        )
    }
}

