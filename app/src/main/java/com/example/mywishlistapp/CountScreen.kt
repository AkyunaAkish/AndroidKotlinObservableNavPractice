package com.example.mywishlistapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CountScreen(countObservable: CountObservable) {
    // Create and remember the Observer instances
    val displayObserver = remember { CountDisplayObserver() }
    var displayCount by remember { mutableIntStateOf(0) }

    // Register the observer when the composable is created
    DisposableEffect(countObservable) {
        countObservable.registerObserver(displayObserver)

        // Update the display count whenever the observer receives an update
        val observer = object : CounterObserver {
            override fun onCountUpdated(count: Int) {
                displayCount = count
            }
        }

        countObservable.registerObserver(observer)

        onDispose {
            countObservable.removeObserver(displayObserver)
            countObservable.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Count: $displayCount",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { countObservable.increment() }) {
                Text("Increment")
            }

            Button(onClick = { countObservable.decrement() }) {
                Text("Decrement")
            }
        }
    }
}