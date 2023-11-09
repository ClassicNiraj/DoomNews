package com.example.doomnews.ui

import androidx.activity.compose.BackHandler
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.doomnews.R
import com.example.doomnews.data.DataSource
import com.example.doomnews.model.NewsArticle
import com.example.doomnews.ui.theme.DoomNewsTheme

@Composable
fun DoomNewsApp() {
    val viewModel: DoomNewsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    // TODO: Add contentType and when conditional to determine windowSize

    Scaffold(
        topBar = {
            DoomNewsAppBar(
                onBackButtonClick = { viewModel.navigateToListPage() },
                isShowingListPage = uiState.isShowingListPage
            )
        }
    ) { innerPadding ->
        // TODO: Add simple navigation with if/else conditional to show details
        // TODO: Add navigation to go to Feed page and List and Details page

        DoomNewsList(
            articles = uiState.articlesList,
            onClick = {
                      /* TODO: Call ViewModel to updateCurrentArticle and navigateToDetailPage */
            },
            contentPadding = innerPadding,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoomNewsAppBar(
    onBackButtonClick: () -> Unit,
    isShowingListPage: Boolean,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = if (!isShowingListPage) {
                    stringResource(R.string.app_name) + " - Article"
                } else {
                    stringResource(R.string.app_name)
                }
            )
        },
        navigationIcon = if (!isShowingListPage) {
            {
                IconButton(onClick = onBackButtonClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        } else {
            { Box {} }
        },
        //colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = modifier,
    )
}

@Composable
fun DoomNewsList(
    articles: List<NewsArticle>,
    onClick: (NewsArticle) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        items(articles) { article ->
            DoomNewsListItem(
                newsArticle = article,
                onItemClick = onClick,
                //modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoomNewsListItem(
    newsArticle: NewsArticle,
    onItemClick: (NewsArticle) -> Unit,
    modifier: Modifier = Modifier
) {
    // Card, Column, Image, Text, Text
    Card(
        modifier = modifier,
        onClick = { onItemClick(newsArticle) }
    ) {
        Column(

        ) {
            DoomNewsImage(imageResourceId = newsArticle.imageResourceId)
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(newsArticle.headlineResourceId),
                style = MaterialTheme.typography.titleLarge,
                minLines = 3,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
            )
            Text(
                text = stringResource(id = R.string.author_line,
                    stringResource(newsArticle.authorResourceId), newsArticle.lastUpdateTime),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp
                )
            )
        }
    }
}

@Composable
private fun DoomNewsDetail(
    selectedArticle: NewsArticle,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    @DimenRes imageSize: Int = R.dimen.image_size_small
) {
    /* TODO: Add Back Handler */

    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .padding(contentPadding)
    ) {
        DoomNewsImage(imageResourceId = selectedArticle.imageResourceId, imageSize)
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(id = selectedArticle.headlineResourceId),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(
                id = R.string.author_line,
                stringResource(selectedArticle.authorResourceId), selectedArticle.lastUpdateTime
            ),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Divider(thickness = 1.dp, modifier = Modifier.padding(8.dp))
        Text(
            text = stringResource(id = selectedArticle.articleResourceId),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(
                vertical = 24.dp,
                horizontal = 40.dp
            )
        )
    }
}

// TODO: Add DoomNewsFeed() composable

// TODO: Add DoomNewsListAndDetails() composable

@Composable
private fun DoomNewsImage(
    @DrawableRes imageResourceId: Int,
    @DimenRes imageSize: Int = R.dimen.image_size_small
) {
    Image(
        painter = painterResource(imageResourceId),
        contentDescription = null,
        modifier = Modifier
            .height(dimensionResource(imageSize))
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
fun DoomNewsListPreview() {
    DoomNewsTheme {
        Surface {
            DoomNewsList(
                articles = DataSource.articles,
                onClick = { }
            )
        }
    }
}

@Preview
@Composable
fun DoomNewsListItemPreview() {
    DoomNewsTheme {
        Surface {
            DoomNewsListItem(
                newsArticle = DataSource.articles[0],
                onItemClick = { }
            )
        }
    }
}

@Preview
@Composable
fun DoomNewsDetailPreview() {
    DoomNewsTheme {
        Surface {
            DoomNewsDetail(
                selectedArticle = DataSource.articles[0],
                onBackPressed = { }
            )
        }
    }
}

// TODO: Add DoomNewsListAndDetailPreview() Preview Composable