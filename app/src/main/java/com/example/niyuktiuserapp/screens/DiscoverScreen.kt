package com.example.niyuktiuserapp.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.niyuktiuserapp.R
import com.example.niyuktiuserapp.model.ApplicantDetailItem
import com.example.niyuktiuserapp.viewmodels.DiscoveryViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

@Preview
@Composable
fun DiscoverScreen() {
    val viewModel = hiltViewModel<DiscoveryViewModel>()
    val applicantList = viewModel.getDummyApplicantDetails()
    var currentIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    val offsetX = remember { Animatable(0f) }
    val swipeThreshold = 250f
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
            .graphicsLayer {
                translationX = offsetX.value
                rotationZ = offsetX.value / 25
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                        }
                    },
                    onDragEnd = {
                        scope.launch {
                            if (offsetX.value > swipeThreshold) {
                                offsetX.animateTo(1000f, tween(300))
                                currentIndex =
                                    (currentIndex + 1).coerceAtMost(applicantList.lastIndex)
                                offsetX.snapTo(0f)
                            } else if (offsetX.value < swipeThreshold) {
                                offsetX.animateTo(-1000f, tween(300))
                                currentIndex =
                                    (currentIndex + 1).coerceAtMost(applicantList.lastIndex)
                                offsetX.snapTo(0f)
                            } else {
                                offsetX.animateTo(0f, spring())
                            }
                        }
                    }
                )
            }
        ) {
            ApplicantProfile(applicantList[currentIndex])
        }
        ActionBar()
    }

}

@Composable
private fun ApplicantProfile(info: ApplicantDetailItem) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp,
            pressedElevation = 2.dp
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                ) {
                    val imageList = listOf(R.drawable.images, R.drawable.image2, R.drawable.image3, R.drawable.image4)
                    val idx = Random.nextInt(0, imageList.size)
                    Image(
                        painter = painterResource(imageList[idx]),
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = info.name,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(15.dp, 8.dp)
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(15.dp, 26.dp)) {
                    Text(
                        text = info.designation,
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Medium
                    )
                    ApplicantMetaData(info)
                }
            }

            item {
                info.applicantInfo.forEach {
                    Column(modifier = Modifier.padding(top = 9.dp, start = 15.dp, end = 15.dp)) {
                        DetailHolder("${it.key}:", it.value)
                    }
                }
            }
        }
    }
}

@Composable
private fun ActionBar() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        ActionHandler(Color.Red, Icons.Filled.ThumbDown, "Dislike")
        ActionHandler(Color.Gray, Icons.Filled.Share, "Share")
        ActionHandler(Color.Green, Icons.Filled.ThumbUp, "Like")
    }
}

@Composable
private fun ApplicantMetaData(info: ApplicantDetailItem) {
    Column(modifier = Modifier.padding(top = 19.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconWithText(Icons.Outlined.LocationOn, "Location", info.location)
            IconWithText(Icons.Outlined.BusinessCenter, "Works Preference", info.workPreference)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 9.dp)
        ) {
            IconWithText(Icons.Outlined.Schedule, "Experience", info.experience)
            Text(
                text = info.noticePeriod,
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF00A63E),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun RowScope.IconWithText(imageVector: ImageVector, contentDesc: String, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
        Image(
            imageVector = imageVector,
            contentDescription = contentDesc,
            colorFilter = ColorFilter.tint(Color(0xFF4A5565)),
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF4A5565),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun DetailHolder(title: String, details: String) {
    Column(modifier = Modifier.padding(0.dp, 8.dp)) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF787878)
        )
        Text(
            text = details,
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF787878),
            modifier = Modifier.padding(top = 9.dp)
        )
    }
}

@Composable
private fun ActionHandler(bgColor: Color, icon: ImageVector, contentDesc: String) {
    Box(
        modifier = Modifier
            .shadow(
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
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Center)
        )
    }
}
