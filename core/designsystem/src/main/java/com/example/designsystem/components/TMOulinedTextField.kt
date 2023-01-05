package com.example.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.White

@Composable
fun TMOutlinedTextField(
    value: String,
    onValueChange:(String) -> Unit,
    isError: Boolean,
    placeholder: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Black.copy(alpha = 0.1f),
                ambientColor = Black.copy(alpha = 0.1f),
            ),
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        textStyle = MaterialTheme.typography.body2,
        singleLine = true,
        placeholder = placeholder,
        shape = RoundedCornerShape(16.dp),
        leadingIcon = leadingIcon,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            textColor = MaterialTheme.colors.onBackground,
            unfocusedBorderColor = White,
            cursorColor = MaterialTheme.colors.onBackground
        )
    )
}