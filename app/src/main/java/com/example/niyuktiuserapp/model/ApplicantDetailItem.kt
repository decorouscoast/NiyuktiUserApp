package com.example.niyuktiuserapp.model

data class ApplicantDetailItem(
    val name: String,
    val imageUrl: String,
    val designation: String,
    val location: String,
    val workPreference: String,
    val experience: String,
    val noticePeriod: String,
    val applicantInfo: HashMap<String, String>
)
