package com.touhidapps.room

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.touhidapps.room.db.entiry.MyTransaction
import com.touhidapps.room.utils.roundToFourDecimals
import com.touhidapps.room.viewModel.TransactionViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
@Preview
fun App() {

    MaterialTheme {

        MainUI()

    }

}


@Composable
fun MainUI(transactionViewModel: TransactionViewModel = koinViewModel()) {

    val upsert by transactionViewModel.upsert.collectAsState()
    val deleteAll by transactionViewModel.deleteAll.collectAsState()
    val delete by transactionViewModel.delete.collectAsState()
    val transactions by transactionViewModel.transactions.collectAsState()

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var isIncome by remember { mutableStateOf(false) }
    var alertMsg by remember { mutableStateOf("") }


    Column(
        Modifier.fillMaxWidth().background(color = Color.White).padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Money Manager",
            textAlign = TextAlign.Center,
            fontSize = 17.sp,
            modifier = Modifier.fillMaxWidth().background(color = Color.Gray).padding(8.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = {
                title = it
            },
            label = {
                Text("Transaction title")
            },
            placeholder = {
                Text("Transaction title")
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = amount,
            textStyle = TextStyle(

            ),
            onValueChange = {
                if (it.matches(Regex("^\\d*\\.?\\d*\$"))) {
                    amount = it
                }
            },
            label = {
                Text("Amount")
            },
            placeholder = {
                Text("Amount")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .clickable { isIncome = !isIncome } // Allows clicking the text to toggle
        ) {
            Checkbox(
                checked = isIncome,
                onCheckedChange = { isIncome = it }
            )
            Text(
                text = "Is Income?",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if(alertMsg.isNotEmpty()) {
            Text(alertMsg)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                alertMsg = ""
                if(title.isEmpty()) {
                    alertMsg = "Title required"
                } else if(amount.isEmpty()) {
                    alertMsg = "amount required"
                }

                if (alertMsg.isEmpty()) {
                    transactionViewModel.upsert(myTransaction = MyTransaction(title = title, amount = amount.toDouble(), isIncome = isIncome))

                    title = ""
                    amount = ""
                }

            }
        ) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(10.dp))

        TransactionList(transactions)


    }

} // MainUI


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionList(items: List<MyTransaction>) {
    LazyColumn {
        stickyHeader {

            val itemsCalc = items.toList() // toList() is to make items immutable so filter will not make any change of main item to show in the list

            val inc = itemsCalc.filter { it.isIncome }.sumOf { it.amount }
            val exp = itemsCalc.filter { !it.isIncome }.sumOf { it.amount }
            val balance = inc - exp

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Total Income: ${inc.roundToFourDecimals()}",
                    color = Color.White
                )
                Text(
                    text = "Total Expense: -${exp.roundToFourDecimals()}",
                    color = Color.White
                )
                Text(
                    text = "Remaining Balance: ${balance.roundToFourDecimals()}",
                    color = Color.White
                )
            }
        }

        items(items.toList().reversed()) { item ->
            Text(
                text = "${item.title} - ${item.amount.roundToFourDecimals()} Tk. - ${ if(item.isIncome)  "Income" else "Expense" }",
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                fontSize = 13.sp
            )
        }

    }
}
