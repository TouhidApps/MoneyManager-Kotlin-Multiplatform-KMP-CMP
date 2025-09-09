package com.touhidapps.room

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.touhidapps.room.domain.model.MyTransaction
import com.touhidapps.room.presentation.common.SharedContract
import com.touhidapps.room.presentation.common.SharedViewModel
import com.touhidapps.room.presentation.home.HomeScreen
import com.touhidapps.room.utils.formatMillisDateOnly
import com.touhidapps.room.utils.roundToFourDecimals
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(sharedContract: SharedContract = koinViewModel<SharedViewModel>()) {

    MaterialTheme {

        val snackBarHostState = remember { SnackbarHostState() }

        // Collect snackbar messages
        LaunchedEffect(Unit) {
            sharedContract.snackbarMessage.collect { message ->
                snackBarHostState.showSnackbar(message)
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
                    sharedContract = sharedContract,
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionList(
    items: List<MyTransaction>,
    onEdit: (MyTransaction) -> Unit,
    onDelete: (MyTransaction) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Sticky Header with Summary
        stickyHeader {
            val itemsCalc = items.toList()
            val inc = itemsCalc.filter { it.isIncome }.sumOf { it.amount }
            val exp = itemsCalc.filter { !it.isIncome }.sumOf { it.amount }
            val balance = inc - exp

            Column(
                modifier = Modifier.fillMaxWidth().background(Color(0xFF37474F)) // Dark gray-blue
                    .padding(12.dp)
            ) {
                Text(
                    text = "Total Income: ${inc.roundToFourDecimals()}",
                    color = Color(0xFF4CAF50), // Green
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Total Expense: (${exp.roundToFourDecimals()})",
                    color = Color(0xFFF44336), // Red
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(
                    color = Color.White.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = "Remaining Balance: ${balance.roundToFourDecimals()}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // List Items
        items(items.toList().reversed()) { item ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = item.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF212121)
                        )
                        Text(
                            text = "${item.amount.roundToFourDecimals()} Tk.",
                            fontSize = 14.sp,
                            color = if (item.isIncome) Color(0xFF4CAF50) else Color(0xFFF44336),
                            fontWeight = FontWeight.Medium
                        )
                        Row {
                            Text(
                                text = if (item.isIncome) "Income" else "Expense",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = if (item.transactionTimeStamp != 0L) " at ${formatMillisDateOnly(item.transactionTimeStamp)}" else "",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    // Edit & Delete Buttons
                    Row {
                        IconButton(onClick = { onEdit(item) }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Color(0xFF1976D2) // Blue
                            )
                        }
                        IconButton(onClick = { onDelete(item) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color(0xFFD32F2F) // Red
                            )
                        }
                    }

                }
            }
        }
    }
}
