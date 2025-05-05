package com.example.healspace

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.healspace.ui.theme.ColorModelMessage
import com.example.healspace.ui.theme.ColorUserMessage

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun ChatPage(modifier: Modifier = Modifier, viewModel: ChatViewModel) {
    val context = LocalContext.current
    val bgColor = remember { Color(ContextCompat.getColor(context, R.color.lesswhite)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        Column(modifier = modifier.fillMaxSize()) {
            AppHeader()
            MessageList(modifier = Modifier.weight(1f), messageList = viewModel.messageList)
            MessageInput(onMessageSend = {
                viewModel.sendMessage(it)
            })
        }
    }
}

@Composable
fun MessageList(modifier: Modifier = Modifier, messageList: List<MessageModel>) {

    if(messageList.isEmpty()) {
        Column (modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Icon(modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.chatbot_message),
                contentDescription = "Icon", tint = ColorModelMessage
            )
            Text(text = "Ask me anything", fontSize = 16.sp, fontWeight = FontWeight.W500,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                color = Color(ContextCompat.getColor(LocalContext.current, R.color.hsmain)))
        }
    } else {
        LazyColumn (modifier = modifier, reverseLayout = true){
            items(messageList.reversed()) {
                MessageRow(messageModel = it)
            }
        }
    }
}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role == "model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxSize() // fillMaxWidth
        ) {
            Box(
                modifier = Modifier
                    .align(if (isModel) Alignment.TopStart else Alignment.TopEnd)
                    .padding(
                        start = if (isModel) 8.dp else 70.dp,
                        end = if (isModel) 70.dp else 8.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    )
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp, topEnd = 16.dp,
                            bottomStart = if (isModel) 4.dp else 16.dp,
                            bottomEnd = if (isModel) 16.dp else 4.dp
                        )
                    )
                    .background(if (isModel) ColorModelMessage else ColorUserMessage)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                SelectionContainer {
                    Text(
                        text = messageModel.message,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.W500,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Composable
fun MessageInput(onMessageSend: (String) -> Unit) {
    var message by remember { mutableStateOf("") }
    val context = LocalContext.current
    val inputTextColor = Color(ContextCompat.getColor(context, R.color.hsmain)) // Custom text color
    val sendIconColor = Color(ContextCompat.getColor(context, R.color.hsmain)) // Custom icon color

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight() // Allows expansion
                .clip(RoundedCornerShape(24.dp)),
            value = message,
            onValueChange = { message = it },
            textStyle = TextStyle(color = inputTextColor),
            maxLines = Int.MAX_VALUE, // Allows multiple lines
            colors = TextFieldDefaults.colors(
                focusedTextColor = inputTextColor,
                unfocusedTextColor = inputTextColor,
                cursorColor = inputTextColor,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = inputTextColor,
                unfocusedIndicatorColor = inputTextColor
            ),
            shape = RoundedCornerShape(24.dp)
        )


        IconButton(onClick = {
            if (message.isNotEmpty()) {
                onMessageSend(message)
                message = ""
            }
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                tint = sendIconColor // Fix send icon color
            )
        }
    }
}


@Composable
fun AppHeader() {
    val context = LocalContext.current
    val headerColor = remember {
        Color(ContextCompat.getColor(context, R.color.lesswhite)) 
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(headerColor)
            .padding(vertical = 13.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Virtual Therapist",
            color = Color(ContextCompat.getColor(context, R.color.hsmain)),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.poppins_bold)),
            textAlign = TextAlign.Center
        )
    }
}


