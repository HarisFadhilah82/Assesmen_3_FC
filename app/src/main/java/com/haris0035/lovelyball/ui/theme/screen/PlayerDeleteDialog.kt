package com.haris0035.lovelyball.ui.theme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun PlayerDeleteDialog(
    playerName: String,
    onDismissRequest: () -> Unit,
    onDeleteConfirmed: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hapus Pemain",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Yakin ingin menghapus pemain \"$playerName\"?",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Batal")
                    }
                    TextButton(onClick = onDeleteConfirmed) {
                        Text("Hapus", color = Color.Red)
                    }
                }
            }
        }
    }
}
