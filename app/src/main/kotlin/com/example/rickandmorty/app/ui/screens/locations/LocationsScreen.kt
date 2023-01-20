package com.example.rickandmorty.app.ui.screens.locations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.rickandmorty.R
import com.example.rickandmorty.app.ui.common.LocationCard
import com.example.rickandmorty.app.ui.common.SearchPanel
import com.example.rickandmorty.app.ui.components.*
import com.example.rickandmorty.app.ui.preview.LocationsPreviewProvider
import com.example.rickandmorty.app.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.domain.models.LocationModel
import rememberLazyListState

@Composable
fun LocationsScreen(
    pagingItems: LazyPagingItems<LocationModel>,
    isLoading: Boolean = false,
    isEmpty: Boolean = false,
    alertDialogVisible: Boolean = false,
    searchValue: String,
    onSearchValueChange: (value: String) -> Unit = {},
    onRetry: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onNavigateToLocation: (locationId: Int) -> Unit = {},
    onBackPress: () -> Unit = {}
) {

    SystemBarsColor()

    val listState = pagingItems.rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                title = {
                    TopAppBarRow(
                        title = stringResource(id = R.string.locations),
                    icon1 = R.drawable.ic_refresh,
                    onIcon1Click = {
                        onRetry()
                    })
            }, navigationIcon = {
                NavigationIcon {
                    onBackPress()
                }
            }, scrollBehavior = scrollBehavior
            )
        }) { scaffoldPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = scaffoldPadding.calculateTopPadding(), start = 16.dp, end = 16.dp
                )
        ) {

            LazyColumn(state = listState) {

                stickyHeader {
                    SearchPanel(
                        searchValue = searchValue, onSearchValueChange = onSearchValueChange
                    )
                }

                items(
                    items = pagingItems,
                    key = {
                        it.id
                    }
                ) { location ->
                    location?.let {
                        LocationCard(it) {
                            onNavigateToLocation(it.id)
                        }
                    }
                }

                item {
                    VerticalSpacer()
                }
            }

            if (isLoading) {
                CenteredLoadingIndicator()
            }
            if (isEmpty) {
                EmptyState(stringResource(id = R.string.no_locations_found))
            }
            if (alertDialogVisible) {
                RetryDialog(
                    errorMessage = stringResource(id = R.string.error_loading_locations),
                    onRetryClick = onRetry,
                    onDismissClick = onDismiss
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationCardPreview() {
    RickAndMortyTheme {
        LocationCard(location = LocationsPreviewProvider().values.first())
    }
}
