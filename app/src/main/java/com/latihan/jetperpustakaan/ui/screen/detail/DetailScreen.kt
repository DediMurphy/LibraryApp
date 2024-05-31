package com.latihan.jetperpustakaan.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithCache {
                                val gradient = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.LightGray),
                                    startY = size.height / 5,
                                    endY = size.height
                                )
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(gradient, blendMode = BlendMode.Multiply)
                                }
                            })
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onBackClick() }
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-32).dp)
                        .background(
                            MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                        )
                        .padding(start = 16.dp, top = 24.dp, end = 16.dp)
                ) {
                    Text(
                        text = nameBook,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                        ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = author, style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Medium
                        ), color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(
                            top = 12.dp, start = 16.dp, end = 16.dp, bottom = 12.dp
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

