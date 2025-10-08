package com.touhidapps.room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.touhidapps.room.presentation.common.CommonActions
import com.touhidapps.room.presentation.common.CommonContract
import com.touhidapps.room.presentation.common.CommonViewModel
import com.touhidapps.room.presentation.home.HomeScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(commonContract: CommonContract = koinViewModel<CommonViewModel>()) {

    MaterialTheme {

        val snackBarHostState = remember { SnackbarHostState() }

        // Collect snackbar messages
        LaunchedEffect(Unit) {
            commonContract.actions.collect { action ->
                when (action) {
                    is CommonActions.ShowSnackbar -> {
                        snackBarHostState.showSnackbar(action.message)
                    }
                }
            }
        }

        Scaffold(
            snackbarHost = { } // disable default bottom snackbar
        ) { paddingValues ->

            Box( // to align snackbar host on top position
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                HomeScreen(
                    commonContract = commonContract,
                )

                // Place snackbar at the TOP
                SnackbarHost(
                    hostState = snackBarHostState,
                    modifier = Modifier.align(Alignment.TopCenter),   // 👈 top position,
                    snackbar = { snackData ->

                        // 🔥 Custom toast UI
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth().height(100.dp)
                                .background(
                                    color = Color(0xFF222222),   // dark background
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(horizontal = 20.dp, vertical = 12.dp) // custom padding
                        ) {
                            Text(
                                text = snackData.visuals.message,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                    }
                )

            }

        }

    }

}

