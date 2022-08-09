package com.blackonedevs.poetry.poems.presentation

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Shapes
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackonedevs.poetry.poems.presentation.PoemViewModel
import com.blackonedevs.poetry.poems.theme.ComposePoetryTheme
import dagger.hilt.android.AndroidEntryPoint

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.blackonedevs.poetry.R
import com.blackonedevs.poetry.poems.core.Constant
import com.blackonedevs.poetry.poems.core.TestTags
import com.blackonedevs.poetry.poems.domain.model.PoemItem
import com.blackonedevs.poetry.poems.presentation.PoemItemState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PoemsListScreen : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposePoetryTheme {
                val viewModel: PoemViewModel = hiltViewModel()
                val state = viewModel.state.value

                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        Box(modifier = Modifier.fillMaxSize())
                        {
                            Column(Modifier.fillMaxSize()) {

                                setupListItems(state)
                            }
                            if (state.isLoading)
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .testTag(TestTags.Loading)
                                )
                            if (state.showRetry) {
                                Box(
                                    modifier = Modifier
                                        .align(
                                            Alignment.BottomEnd
                                        )
                                        .padding(15.dp),
                                ) {
                                    ExtendedFloatingActionButton(
                                        onClick = { scope.launch { viewModel.loadData() } },
                                        icon = {
                                            Icon(
                                                Icons.Filled.Refresh,
                                                contentDescription = "Refresh"
                                            )
                                        },

                                        text = {
                                            Text(
                                                "${state.message ?: ""}",
                                                modifier = Modifier.testTag(
                                                    TestTags.Retry
                                                )
                                            )
                                        }
                                    )

                                }
                            }

                        }
                    }
                }
            }
        }
    }


}
@Composable
private fun setupListItems(state: PoemItemState) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        state.poemItems?.size?.let { it ->
            items(it) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    shape = RoundedCornerShape(15.dp),
                    elevation = 18.dp,
                    backgroundColor = MaterialTheme.colorScheme.surface
                ) {
                    Column(modifier = Modifier.padding(15.dp))
                    {
                        Text(
                            text = state.poemItems[it].title,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.titleMedium,

                            )
                        Spacer(modifier = Modifier.height(8.dp))
                        Spacer(
                            modifier = Modifier
                                .width(250.dp)
                                .height(1.dp)
                                .background(Color.Gray)
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = state.poemItems[it].lines,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = "~${state.poemItems[it].author}",
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(end = 20.dp),
                            style = MaterialTheme.typography.labelMedium,
                        )

                    }
                }

            }
        }

    }
}
