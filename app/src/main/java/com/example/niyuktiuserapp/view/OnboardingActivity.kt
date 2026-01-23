package com.example.niyuktiuserapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.niyuktiuserapp.R
import com.example.niyuktiuserapp.ui.theme.NiyuktiUserAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NiyuktiUserAppTheme {
                JobTantraLoginScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobTantraLoginScreen() {
    val context = LocalContext.current

    var mobileNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var isOtpSent by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Hardcoded OTP for temporary testing
    val hardcodedOtp = "123456"

    // Dynamic spacer height based on whether OTP is shown
    val dynamicSpacerHeight = if (isOtpSent) 10.dp else 34.dp

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "JobTantra",
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF21C07F)
                ),
                modifier = Modifier.height(153.dp)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "Welcome to JobTantra",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Find your dream job in one swipe",
                        fontSize = 18.sp,
                        color = Color(0xFF787878),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "Mobile Number",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = mobileNumber,
                        onValueChange = { mobileNumber = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        placeholder = {
                            Text(
                                "Enter 10-digit mobile number",
                                color = Color(0xFF888888)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        enabled = true, // Always enabled
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black,
                            focusedPlaceholderColor = Color(0xFF797979),
                            unfocusedPlaceholderColor = Color(0xFF797979),
                            cursorColor = Color.Black
                        )
                    )

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Show OTP field only after OTP is sent
                    if (isOtpSent) {
                        Text(
                            text = "Enter OTP",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = otp,
                            onValueChange = { otp = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            placeholder = {
                                Text(
                                    "Enter 6-digit OTP",
                                    color = Color(0xFF888888)
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.Black,
                                focusedPlaceholderColor = Color(0xFF888888),
                                unfocusedPlaceholderColor = Color(0xFF888888),
                                cursorColor = Color.Black
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Login Button (Verify OTP)
                        Button(
                            onClick = {
                                if (otp == hardcodedOtp) {
                                    // Navigate to MainActivity
                                    val intent = Intent(context, MainActivity::class.java)
                                    context.startActivity(intent)
                                    (context as? ComponentActivity)?.finish()
                                } else {
                                    errorMessage = "Invalid OTP. Please enter correct OTP."
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFC107)
                            )
                        ) {
                            Text(
                                text = "Login",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                    } else {
                        // Send OTP Button
                        Button(
                            onClick = {
                                if (isValidMobileNumber(mobileNumber)) {
                                    errorMessage = ""
                                    isOtpSent = true


                                } else {
                                    errorMessage = "Please enter a valid 10-digit mobile number"
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFC107)
                            )
                        ) {
                            Text(
                                text = "SEND OTP",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(dynamicSpacerHeight))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            thickness = 1.dp,
                            color = Color(0xFFE0E0E0)
                        )
                        Text(
                            text = "  OR  ",
                            fontSize = 16.sp,
                            color = Color(0xFF797979)
                        )
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            thickness = 1.dp,
                            color = Color(0xFFE0E0E0)
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    SocialLoginButton(
                        text = "Sign in with Google",
                        iconRes = R.drawable.image2
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    SocialLoginButton(
                        text = "Sign in with Facebook",
                        iconRes = R.drawable.image2
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    SocialLoginButton(
                        text = "Sign in with Apple",
                        iconRes = R.drawable.image2
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "By signing in, you agree to our Terms & Privacy Policy",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

fun isValidMobileNumber(number: String): Boolean {
    return number.length == 10 && number.all { it.isDigit() }
}

@Composable
fun SocialLoginButton(
    text: String,
    iconRes: Int
) {
    OutlinedButton(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFF212121)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = text,
                color = Color(0xFF212121),
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JobTantraLoginPreview() {
    JobTantraLoginScreen()
}