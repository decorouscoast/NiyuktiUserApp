package com.example.niyuktiuserapp.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.BusinessCenter
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.niyuktiuserapp.R
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Preview
@Composable
fun DiscoverScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp,
                pressedElevation = 2.dp
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.images),
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = "Name",
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                    )
                }

                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "Graphic Designer")
                    ApplicantMetaData()
                    DetailHolder("Skills", "Graphic Designer, photoshop, etc")
                    DetailHolder("Current CTC", "12 Lakhs per Annum")
                }

            }
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            ActionHandler(Color.Red, Icons.Filled.ThumbDown, "Dislike")
            ActionHandler(Color.Gray, Icons.Filled.Share, "Share")
            ActionHandler(Color.Green, Icons.Filled.ThumbUp, "Like")
        }
    }

}

@Composable
fun ApplicantMetaData() {
    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconWithText(Icons.Outlined.LocationOn, "Location", "Pune, Maharastra")
            IconWithText(Icons.Outlined.BusinessCenter, "Works Preference", "Full - Time")
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconWithText(Icons.Outlined.Schedule, "Experience", "3-5 Years")
            Text(text = "15 days notice")
        }
    }
}

@Composable
fun IconWithText(imageVector: ImageVector, contentDesc: String, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            imageVector = imageVector,
            contentDescription = contentDesc,
            modifier = Modifier.size(24.dp)
        )
        Text(text = text, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun DetailHolder(title: String, details: String) {
    Column(modifier = Modifier.padding(0.dp, 8.dp)) {
        Text(text = title)
        Text(text = details, modifier = Modifier.padding(4.dp))
    }
}

@Composable
fun ActionHandler(bgColor: Color, icon: ImageVector, contentDesc: String) {
    Box(
        modifier = Modifier.shadow(
            elevation = 12.dp,
            shape = CircleShape,
            clip = true
        )
            .size(60.dp)
            .background(bgColor, shape = CircleShape)
    ) {
        Image(
            imageVector = icon,
            contentDescription = contentDesc,
            modifier = Modifier.size(40.dp).align(Alignment.Center)
        )
    }
}

@Composable
fun SwipeableCard(
    onSwipedLeft: () -> Unit,
    onSwipedRight: () -> Unit,
    content: @Composable () -> Unit
) {
    val offsetX = remember { Animatable(0f) }
    val rotation = offsetX.value / 10f // Subtle tilt while dragging
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
            .graphicsLayer(rotationZ = rotation)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        if (offsetX.value > 400f) { // Right swipe threshold
                            scope.launch {
                                offsetX.animateTo(1000f)
                                onSwipedRight()
                            }
                        } else if (offsetX.value < -400f) { // Left swipe threshold
                            scope.launch {
                                offsetX.animateTo(-1000f)
                                onSwipedLeft()
                            }
                        } else {
                            // Snap back to center
                            scope.launch { offsetX.animateTo(0f) }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                        }
                    }
                )
            }
    ) {
        content()
    }
}

