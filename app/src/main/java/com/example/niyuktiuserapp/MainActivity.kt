package com.example.niyuktiuserapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.niyuktiuserapp.screens.DiscoverScreen
import com.example.niyuktiuserapp.ui.theme.NiyuktiUserAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            NiyuktiUserAppTheme {
                Scaffold(
                    topBar = {
                        TopBar()
                    },
                    bottomBar = {
                        NavBar()
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(top = 16.dp)
                    ) {
                        DiscoverScreen()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(8.dp, 2.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "Profile",
            modifier = Modifier.size(40.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .fillMaxHeight()
                .background(Color.LightGray.copy(alpha = 0.3f), CircleShape)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if ("".isEmpty()) {
                        Text(
                            text = "Search...",
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            )
        }
        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = "Notifications",
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun NavBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Discover"
                )
            },
            onClick = {},
            label = { Text(text = "Discover") }
        )
        NavigationBarItem(
            selected = false,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Jobs"
                )
            },
            onClick = {},
            label = { Text(text = "Jobs") }
        )
        NavigationBarItem(
            selected = false,
            icon = {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Interests"
                )
            },
            onClick = {},
            label = { Text(text = "Interests") }
        )
        NavigationBarItem(
            selected = false,
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Chat,
                    contentDescription = "Message"
                )
            },
            onClick = {},
            label = { Text(text = "Message") }
        )
        NavigationBarItem(
            selected = false,
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Discover"
                )
            },
            onClick = {},
            label = { Text(text = "Discover") }
        )
    }
}