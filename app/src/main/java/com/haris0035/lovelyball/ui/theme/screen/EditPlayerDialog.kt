package com.haris0035.lovelyball.ui.theme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.wear.compose.material3.OutlinedButton
import com.haris0035.lovelyball.model.Player

@Composable
fun EditPlayerDialog(
    player: Player,
    onDismissRequest: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var nama by remember { mutableStateOf(player.nama) }
    var nomor by remember { mutableStateOf(player.no_punggung) }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Edit Pemain", style = MaterialTheme.typography.titleMedium)

                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier.padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = nomor,
                    onValueChange = { nomor = it },
                    label = { Text("No Punggung") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(onClick = onDismissRequest) {
                        Text("Batal")
                    }
                    OutlinedButton(
                        onClick = {
                            onConfirm(nama, nomor)
                        },
                        enabled = nama.isNotEmpty() && nomor.isNotEmpty(),
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Simpan")
                    }
                }
            }
        }
    }
}
