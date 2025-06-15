@file:Suppress("DEPRECATION", "DEPRECATION")

package com.example.aapdasetu.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aapdasetu.R

@Composable
fun UserInputField(a : String, b : String, c : ImageVector?, d : Int, f :(String) -> Unit ) {
    var input by remember { mutableStateOf("") }

    OutlinedTextField(
        value = input,
        onValueChange = { input = it
            f(it)},
        label = { Text(
            a) },
        leadingIcon = if (c != null) {
            {
                Icon(
                    imageVector = c,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        } else null,
        placeholder = { Text(
            b,
            Modifier.alpha(.35f)
        ) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(d.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF0F0F0),
            unfocusedBorderColor = Color(0xFF034F95),
            focusedContainerColor = Color(0xFFF0F0F0),
            focusedBorderColor = Color(0xFF034F95),
            focusedLabelColor = Color(0xFFFC8414)
        )
    )
}

@Composable
fun PasswordField(c :(String) -> Unit ){
    var isPasswordVisible by remember { mutableStateOf(false) }
    var input by remember { mutableStateOf("") }

    OutlinedTextField(
        value = input,
        onValueChange = { input = it
            c(it)},
        label = { Text("Password") },
        placeholder = { Text("Niggachu") },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Lock Icon",
                tint = Color.Gray,
            )
        },
        trailingIcon = {
            val visibilityIcon = if (isPasswordVisible)
                painterResource(id = R.drawable.visible) else painterResource(id = R.drawable.hide)

            val description = if (isPasswordVisible) "Hide password" else "Show password"

            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    painter = visibilityIcon,
                    contentDescription = description,
                    tint = Color.Gray,
                    modifier = Modifier
                )
            }
        },
        visualTransformation = if (isPasswordVisible)
            VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF0F0F0),
            unfocusedBorderColor = Color(0xFF034F95),
            focusedContainerColor = Color(0xFFF0F0F0),
            focusedBorderColor = Color(0xFF034F95),
            focusedLabelColor = Color(0xFFFC8414)
        )
    )
}

@Composable
fun UserDataField(a : String, b : String,c: ImageVector,d :(String) -> Unit ) {

    OutlinedTextField(
        value = a,
        onValueChange = d,
        label = { Text(
            b) },
        placeholder = { Text(a) },
        leadingIcon = {Icon(
            imageVector = c,
            contentDescription = null,
            tint = Color.Gray,
        )},
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF0F0F0),
            unfocusedBorderColor = Color(0xFF034F95),
            focusedContainerColor = Color(0xFFF0F0F0),
            focusedBorderColor = Color(0xFF034F95),
            focusedLabelColor = Color(0xFFFC8414)
        )
    )
}

@Composable
fun Button(modifier : Modifier, b: String,c: FontWeight,d: Int ,z: () -> Unit){
    ElevatedButton(
        modifier = modifier,
        onClick = {z()},
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xFF034F95),
            contentColor = Color.White
        )
    ) {
        APText(b,c,d)
    }
}

@Composable
fun LogoBox(a: Painter, b: String, modifier: Modifier){
    Box {
        Image(
            painter = a,
            contentDescription = b,
            modifier = modifier
        )
    }
}

@Composable
fun APText(a: String,b: FontWeight, c: Int ){
    Text(
        a,
        fontWeight = b,
        fontSize = c.sp

    )
}

@Composable
fun CheckBox(checked: Boolean,onCheckedChange: (Boolean) -> Unit){
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxDefaults.colors(
            checkedColor = Color(0xFF034F95),
            uncheckedColor = Color(0xFFFC8414),
        )
    )
}

@Suppress("DEPRECATION")
@Composable
fun ClickableText( a : String, b: String , c : Color, d : Modifier,z : () -> Unit) {
    val annotatedText = buildAnnotatedString {
        append(a)

        pushStringAnnotation(tag = "SIGNUP", annotation = "signup")
        withStyle(style = SpanStyle(color = c, fontWeight = FontWeight.SemiBold)) {
            append(b)
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = {z()
        },
        modifier = d,
        style = LocalTextStyle.current.copy(fontSize = 16.sp)

    )
}

@Composable
fun UserAgreementDraft(showDialog : Boolean, onCancel : () -> Unit, onAgree : ()-> Unit){
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onCancel,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = ""
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("User Agreement") }},
            text = { Text("The AapdaSetu app offer only accurate and authentic reports of Emergency Situation.\n\n" +
                    "Information provided by the User must be genuine situations. \n" +
                    "Deliberate submission of false or misleading information may lead to punishable offense under \n\n" +
                    "Section 182 of the Indian Penal Code (IPC) — \nLeads to imprisonment for up to 6 months,\n" +
                    "A monetary fine, or both. \n\n" +
                    "To Use the app, Agree these conditions and legal responsibilities.") },
            confirmButton = {
                TextButton(onClick = {
                    onAgree()
                }) {
                    Text("Agree")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onCancel()
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ProfileIcon( width : Int , a: Painter, size: Int, z : () -> Unit){
    Box(
        modifier = Modifier
            .border(width.dp, Color(0xFF034F95), shape = CircleShape)
            .clip(CircleShape)
            .clickable(onClick = z)
            .size(size.dp)

    ){
        Image(
            painter = a,
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

