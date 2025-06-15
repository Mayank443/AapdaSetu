package com.example.aapdasetu.ui.view.report

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.aapdasetu.R
import com.example.aapdasetu.ui.view.APText
import com.example.aapdasetu.ui.view.LogoBox
import kotlin.collections.forEach

@Composable
fun Header(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBack) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
        }
        APText("AapdaSetu", FontWeight.Bold, 48)
        Spacer(Modifier.width(16.dp))
        LogoBox(painterResource(id = R.drawable.logo), "", Modifier.size(80.dp))
    }
}

@Composable
fun ImageUploader(
    imageUris: List<Uri>,
    screenWidth: Dp,
    launcher: ActivityResultLauncher<String>
) {
    Box(
        modifier = Modifier
            .border(1.5.dp, Color(0xFF034F95), shape = RectangleShape)
            .background(Color(0xFFF0F0F0), shape = RectangleShape)
            .padding(16.dp)
            .fillMaxWidth()
            .height(240.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            imageUris.forEach {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    modifier = Modifier
                        .width(screenWidth / 7 * 2)
                        .fillMaxHeight()
                )
            }

            if (imageUris.size < 3) {
                IconButton(onClick = { launcher.launch("image/*") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.add_image),
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun DisasterTypeDropdown(
    items: List<String>,
    selected: String,
    customValue: String,
    onCustomChange: (String) -> Unit,
    onSelectedChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = if (selected == "Custom") customValue
            else selected,
            onValueChange = {
                if (selected == "Custom") {
                    onCustomChange(it)
                }
            },
            label = { Text("Disaster Type") },
            readOnly = selected != "Custom",
            placeholder = {
                if (selected == "Custom")
                    Text("Enter your Problem", Modifier.alpha(.35f))
                else
                    Text("Select From the list =>", Modifier.alpha(.35f))
            },
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color.Gray)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF0F0F0),
                unfocusedBorderColor = Color(0xFF034F95),
                focusedContainerColor = Color(0xFFF0F0F0),
                focusedBorderColor = Color(0xFF034F95),
                focusedLabelColor = Color(0xFFFC8414)
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { label ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onSelectedChange(label)
                        if (label != "Custom") onCustomChange("") // Clear custom if not selected
                        expanded = false
                    }
                )
            }
        }
    }
}