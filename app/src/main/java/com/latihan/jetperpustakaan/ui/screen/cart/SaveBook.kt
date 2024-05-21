package com.latihan.jetperpustakaan.ui.screen.cart

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.latihan.jetperpustakaan.R
import com.latihan.jetperpustakaan.ViewModelFactory
import com.latihan.jetperpustakaan.di.Injection
import com.latihan.jetperpustakaan.ui.common.UiState
import com.latihan.jetperpustakaan.ui.theme.JetPerpustakaanTheme


@Composable
fun SaveBook(
    bookId: Long,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),

) {
   viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
       when (uiState) {
           is UiState.Loading -> {
               viewModel.getBooksId(bookId)
           }
           is UiState.Success -> {
               val data = uiState.data
               SaveBookContent(
                   image = data.reward.photoUrl,
                   nameBook = data.reward.nameBook,
                   author = data.reward.author,
                   modifier = modifier
               )
           }
           is UiState.Error -> {}
       }
   }
}

@Composable
fun SaveBookContent(
    @DrawableRes image: Int,
    nameBook: String,
    author: String,
    modifier: Modifier = Modifier
){
  Row(
      modifier = modifier
          .padding(8.dp)
          .fillMaxSize()
  ) {
      Image(
          painter = painterResource(image),
          contentDescription = "Name Book",
          modifier = modifier
              .size(60.dp)
              .clip(CircleShape)
      )
      
      Spacer(modifier = modifier.width(8.dp))
      Column {
          Text(
              text = nameBook,
              color = MaterialTheme.colorScheme.secondary,
              style = MaterialTheme.typography.titleMedium
          )
          Spacer(modifier = modifier.height(4.dp))
          Text(
              text = author,
              color = MaterialTheme.colorScheme.tertiary,
              style = MaterialTheme.typography.bodySmall
          )
      }
  }
}

@Preview
@Composable
fun SavePreview() {
    JetPerpustakaanTheme() {
        SaveBookContent(R.drawable.atomic_habits, nameBook = "James", author = "call me")
    }
}