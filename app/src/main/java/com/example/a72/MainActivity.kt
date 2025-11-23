package com.example.a72

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiCounterApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiCounterApp() {
    var counters by remember { mutableStateOf(listOf<Int>()) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Multi Counter App") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { counters = counters + 0 }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(counters) { index, value ->
                    CounterItem(
                        name = "Counter_${index + 1}",
                        value = value,
                        onIncrement = { counters = counters.mapIndexed { i, v -> if (i == index) v + 1 else v } },
                        onDecrement = { counters = counters.mapIndexed { i, v -> if (i == index) v - 1 else v } },
                        onRemove = { counters = counters.filterIndexed { i, _ -> i != index } }
                    )
                }
            }
        }
    }
}

@Composable
fun CounterItem(
    name: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp)
        ) {
            Column {
                Text(text = name, fontSize = 20.sp)
                Text(text = "Value: $value", fontSize = 16.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = onDecrement, modifier = Modifier.padding(end = 8.dp)) {
                    Text("-")
                }
                Button(onClick = onIncrement, modifier = Modifier.padding(end = 8.dp)) {
                    Text("+")
                }
                Text(
                    text = "Remove",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .clickable { onRemove() }
                        .padding(8.dp)
                )
            }
        }
    }
}
