package com.example.cinema_ticketing_app.module.seat_selector.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cinema_ticketing_app.core.theme.Gray
import com.example.cinema_ticketing_app.core.theme.LightGray
import com.example.cinema_ticketing_app.core.theme.Yellow
import java.time.LocalDate

@Composable
fun SeatSelectorScreen(
    navController: NavController,
) {
    val today = LocalDate.now()
    val dateScrollState = rememberScrollState()
    val timeScrollState = rememberScrollState()

    val selectedSeat = remember {
        mutableStateListOf<String>()
    }

    val selectedDate = remember {
        mutableStateOf<LocalDate?>(null)
    }

    val selectedTime = remember {
        mutableStateOf<String?>(null)
    }

    Scaffold(
            backgroundColor = LightGray
            ) { padding ->
        Column(
            modifier = androidx.compose.ui.Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 16.dp, vertical = 8.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Select Seat", style = MaterialTheme.typography.h6)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 48.dp, top = 8.dp)
                    .background(color = Yellow)
                    .fillMaxWidth(0.5f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Screen",
                    style = MaterialTheme.typography.body2.copy(color = Color.White)
                )
            }
            for (i in 1..6) {
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    for (j in 1..8) {
                        val seatNumber = "${(64 + i).toChar()}$j"
                        SeatComp(
                            isEnabled = i != 6,
                            isSelected = selectedSeat.contains(seatNumber),
                            seatNumber = seatNumber
                        ) { selected, seat ->
                            if (selected) {
                                selectedSeat.remove(seat)
                            } else {
                                selectedSeat.add(seat)
                            }
                        }

                        if (j != 8) Spacer(modifier = Modifier.width(if (j == 4) 16.dp else 8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
            /// indicator
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {

                SeatComp(isEnabled = false)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Reserved",
                    style = MaterialTheme.typography.caption,
                )

                Spacer(modifier = Modifier.width(16.dp))

                SeatComp(isEnabled = true, isSelected = true)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Selected",
                    style = MaterialTheme.typography.caption,
                )

                Spacer(modifier = Modifier.width(16.dp))

                SeatComp(isEnabled = true, isSelected = false)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Available",
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}
@Composable
fun SeatComp(
    isEnabled: Boolean = false,
    isSelected: Boolean = false,
    seatNumber: String = "",
    onClick: (Boolean, String) -> Unit = { _, _ -> },
) {
    val seatColor = when {
        !isEnabled -> Color.Gray
        isSelected -> Yellow
        else -> Color.White
    }

    val textColor = when {
        isSelected -> Color.White
        else -> Color.Black
    }

    Box(modifier = Modifier
        .size(32.dp)
        .border(width = 1.dp, color = Gray, shape = RoundedCornerShape(8.dp))
        .clip(RoundedCornerShape(8.dp))
        .background(color = seatColor)
        .clickable {
            onClick(isSelected, seatNumber);
        }
        .padding(8.dp), contentAlignment = Alignment.Center) {
        Text(
            seatNumber,
            style = MaterialTheme.typography.caption.copy(color = textColor),
        )
    }
}