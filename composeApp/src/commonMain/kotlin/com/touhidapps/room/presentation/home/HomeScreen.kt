package com.touhidapps.room.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.touhidapps.room.data.datasource.local.db.dao.TransactionDao
import com.touhidapps.room.data.datasource.local.db.entity.SummaryEntity
import com.touhidapps.room.data.datasource.local.db.entity.TransactionEntity
import com.touhidapps.room.domain.model.Transaction
import com.touhidapps.room.domain.usecase.TransactionDeleteUseCase
import com.touhidapps.room.domain.usecase.TransactionGetAllUseCase
import com.touhidapps.room.domain.usecase.TransactionUpsertUseCase
import com.touhidapps.room.presentation.common.CommonContract
import com.touhidapps.room.presentation.common.CommonViewModel
import com.touhidapps.room.presentation.component.CustomAlertDialog
import com.touhidapps.room.data.repo.TransactionRepositoryImpl
import com.touhidapps.room.domain.model.Summary
import com.touhidapps.room.domain.usecase.TransactionSummaryUseCase
import com.touhidapps.room.presentation.common.CommonEvents
import com.touhidapps.room.utils.formatMillisDateOnly
import com.touhidapps.room.utils.endOfTodayMillis
import com.touhidapps.room.utils.roundToTwoDecimals
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    commonContract: CommonContract = koinViewModel<CommonViewModel>(),
    homeContract: HomeContract = koinViewModel<TransactionViewModel>() // no need to use HomeViewModel instead of use homeContract
) {

    val state by homeContract.state.collectAsStateWithLifecycle() // collectAsState() for reactive recomposition.

    // The dialog is purely a UI concern.
    // It doesn’t belong to your domain/business logic — the user can cancel anytime without affecting data.
    // so no need to keep it in state
    var showDeleteDialog by remember { mutableStateOf<Transaction?>(null) }

    var showDatePicker by remember { mutableStateOf(false) }

    val todayEndMillis by remember { mutableStateOf(endOfTodayMillis()) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = todayEndMillis, // or todayStart depending on UX
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                // Allow today and past days only
                return utcTimeMillis <= todayEndMillis
            }
        }
    )

    LaunchedEffect(Unit) {

        // Set default date once
        homeContract.onEvent(HomeEvents.ChangeDate(endOfTodayMillis()))

        // Collect upsert
        launch {
            homeContract.state
                .distinctUntilChangedBy { it.selectedDate } // to avoid multiple calls when isLoading is false multiple times.
                .map { it.selectedDate }
                .collect { selectedDate ->

                    datePickerState.selectedDateMillis = selectedDate
                    datePickerState.displayMode = DisplayMode.Picker // to show current selected date page

                }
        }

        launch {
            homeContract.actions.collect { action ->
                when (action) {
                    is HomeActions.ShowSnackbar -> {
                        // forward to common VM
                        commonContract.onEvent(CommonEvents.ShowSnackbar(action.message))
                    }
                }
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Text(
            text = "💰 Money Manager",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Transaction Title
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.title ?: "",
            onValueChange = {
                homeContract.onEvent(HomeEvents.ChangeTitle(it))
            },
            label = { Text("Transaction Title") },
            placeholder = { Text("ex: Salary, Grocery, Rent") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Transaction Amount
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.amount ?: "",
            onValueChange = {
                if (it.matches(Regex("^\\d*\\.?\\d*\$"))) {
                    homeContract.onEvent(HomeEvents.ChangeAmount(it))
                }
            },
            label = { Text("Amount") },
            placeholder = { Text("Enter amount") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Transaction Type (Income/Expense)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    homeContract.onEvent(HomeEvents.ChangeIsIncome(!state.isIncome))
                }
                .padding(4.dp)
        ) {
            Checkbox(
                checked = state.isIncome,
                onCheckedChange = {
                    homeContract.onEvent(HomeEvents.ChangeIsIncome(it))
                }
            )
            Text(
                text = if (state.isIncome) "Income ↙️" else "Expense ↗️",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = if (state.isIncome) Color(0xFF4CAF50) else Color(0xFFF44336)
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Date Picker
        OutlinedButton(
            onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
            Spacer(Modifier.width(8.dp))
            Text(
                text = formatMillisDateOnly(state.selectedDate ?: endOfTodayMillis()),
                fontWeight = FontWeight.Medium
            )
        }


        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            homeContract.onEvent(HomeEvents.ChangeDate(millis))
                        }
                        showDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Spacer(modifier = Modifier.height(12.dp))

        // Save Button
        Row {
            Button(
                onClick = {

                    homeContract.onEvent(HomeEvents.SaveButtonClick)

                },
                modifier = Modifier.wrapContentHeight().weight(1F),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Default.Save, contentDescription = "Save")
                Spacer(Modifier.width(6.dp))
                Text(
                    text = if (state.itemIdForUpdate == null) "Save Transaction" else "Update Transaction"
                )
            }
            if (state.itemIdForUpdate != null) {
                Button(onClick = {

                    homeContract.onEvent(HomeEvents.CancelUpdateClick)

                    datePickerState.selectedDateMillis = endOfTodayMillis()
                    datePickerState.displayMode = DisplayMode.Picker // to show current selected date page

                }) {
                    Text("Cancel Update")
                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            // Transactions List
            TransactionList(
                items = homeContract.state.value.transactions,
                summary = homeContract.state.value.summary,
                onEdit = { transaction ->

                    homeContract.onEvent(HomeEvents.EditItemButtonClick(transaction))

                    datePickerState.displayMode = DisplayMode.Picker // To show current selected date page

                },
                onDelete = {

                    showDeleteDialog = it

                },
            )

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp), // adjust size
                    color = MaterialTheme.colorScheme.primary, // or your custom color
                    strokeWidth = 4.dp
                )
            }

        }



        if (showDeleteDialog != null) {
            CustomAlertDialog(
                description = "Are you sure you want to delete this item ${showDeleteDialog?.title} (${showDeleteDialog?.amount})? This action cannot be undone.",
                onDismiss = {
                    showDeleteDialog = null
                },
                onConfirm = {
                    homeContract.onEvent(HomeEvents.DeleteItemYesClick(showDeleteDialog))
                    showDeleteDialog = null
                },
                onCancel = {
                    showDeleteDialog = null
                }
            )
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionList(
    items: List<Transaction>,
    summary: Summary?,
    onEdit: (Transaction) -> Unit,
    onDelete: (Transaction) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Sticky Header with Summary
        stickyHeader {

            Column(
                modifier = Modifier.fillMaxWidth().background(Color(0xFF37474F)) // Dark gray-blue
                    .padding(12.dp)
            ) {
                Text(
                    text = "Total Income: ${summary?.totalIncome?.roundToTwoDecimals() ?: 0}",
                    color = Color(0xFF4CAF50), // Green
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Total Expense: (${summary?.totalExpense?.roundToTwoDecimals() ?: 0})",
                    color = Color(0xFFF44336), // Red
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(
                    color = Color.White.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = "Remaining Balance: ${summary?.balance?.roundToTwoDecimals() ?: 0}",
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
                            text = "${item.amount.roundToTwoDecimals()} Tk.",
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


@Preview
@Composable
fun HomeScreenPreview() {

    HomeScreen(
        commonContract = CommonViewModel(),
        homeContract = TransactionViewModel(

            transactionUpsertUseCase = TransactionUpsertUseCase(
                TransactionRepositoryImpl(
                    FakeTransactionDao()
                )
            ),
            transactionGetAllUseCase = TransactionGetAllUseCase(
                TransactionRepositoryImpl(
                    FakeTransactionDao()
                )
            ),
            transactionDeleteUseCase = TransactionDeleteUseCase(
                TransactionRepositoryImpl(
                    FakeTransactionDao()
                )
            ),
            transactionSummaryUseCase = TransactionSummaryUseCase(
                TransactionRepositoryImpl(
                    FakeTransactionDao()
                )
            ),
        )
    )

}

class FakeTransactionDao : TransactionDao {
    override suspend fun upsert(transaction: TransactionEntity) { /* no-op */ }

    override suspend fun getAllTransaction(): List<TransactionEntity> {
        return listOf(
            TransactionEntity(id = 1, title = "Salary", amount = 5.0, isIncome = true, transactionTimeStamp = endOfTodayMillis(), entryTimeStamp = ""),
            TransactionEntity(id = 2, title = "Groceries", amount = 20.0, isIncome = false, transactionTimeStamp = endOfTodayMillis(), entryTimeStamp = "")
        )
    }

    override suspend fun deleteAll() {}
    override suspend fun delete(transaction: TransactionEntity) {}
    override suspend fun getSummary(): SummaryEntity {
        return SummaryEntity(0.0,0.0,0.0)
    }

}
