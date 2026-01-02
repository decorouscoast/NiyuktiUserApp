package com.example.niyuktiuserapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.niyuktiuserapp.model.ApplicantDetailItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(): ViewModel() {
    private val _applicantDetailList = MutableStateFlow<List<ApplicantDetailItem>>(getDummyApplicantDetails())
    val applicantDetailList : StateFlow<List<ApplicantDetailItem>>
        get() = _applicantDetailList

    fun getDummyApplicantDetails(): List<ApplicantDetailItem> {
        return listOf(
            ApplicantDetailItem(
                name = "John Doe",
                imageUrl = "https://via.placeholder.com/150",
                designation = "Software Engineer",
                location = "New York, USA",
                workPreference = "Full-time",
                experience = "5 years",
                noticePeriod = "30 days",
                applicantInfo = hashMapOf(
                    "Age" to "28",
                    "Education" to "B.Tech in Computer Science",
                    "Skills" to "Java, Kotlin, Android Development",
                    "LinkedIn" to "https://www.linkedin.com/in/johndoe",
                    "Portfolio" to "https://johndoe-portfolio.com"
                )
            ),
            ApplicantDetailItem(
                name = "Jane Smith",
                imageUrl = "https://via.placeholder.com/150",
                designation = "Graphic Designer",
                location = "San Francisco, USA",
                workPreference = "Part-time",
                experience = "3 years",
                noticePeriod = "15 days",
                applicantInfo = hashMapOf(
                    "Age" to "26",
                    "Education" to "BFA in Graphic Design",
                    "Skills" to "Photoshop, Illustrator, UI/UX Design",
                    "LinkedIn" to "https://www.linkedin.com/in/janesmith",
                    "Portfolio" to "https://janesmith-portfolio.com"
                )
            ),
            ApplicantDetailItem(
                name = "Alice Johnson",
                imageUrl = "https://via.placeholder.com/150",
                designation = "Data Scientist",
                location = "London, UK",
                workPreference = "Remote",
                experience = "7 years",
                noticePeriod = "60 days",
                applicantInfo = hashMapOf(
                    "Age" to "32",
                    "Education" to "MSc in Data Science",
                    "Skills" to "Python, Machine Learning, TensorFlow",
                    "LinkedIn" to "https://www.linkedin.com/in/alicejohnson",
                    "Portfolio" to "https://alicejohnson-portfolio.com"
                )
            ),
            ApplicantDetailItem(
                name = "Bob Brown",
                imageUrl = "https://via.placeholder.com/150",
                designation = "Product Manager",
                location = "Sydney, Australia",
                workPreference = "Full-time",
                experience = "10 years",
                noticePeriod = "90 days",
                applicantInfo = hashMapOf(
                    "Age" to "40",
                    "Education" to "MBA in Product Management",
                    "Skills" to "Agile, Scrum, Product Lifecycle Management",
                    "LinkedIn" to "https://www.linkedin.com/in/bobbrown",
                    "Portfolio" to "https://bobbrown-portfolio.com"
                )
            ),
            ApplicantDetailItem(
                name = "Charlie Davis",
                imageUrl = "https://via.placeholder.com/150",
                designation = "Marketing Specialist",
                location = "Toronto, Canada",
                workPreference = "Part-time",
                experience = "4 years",
                noticePeriod = "20 days",
                applicantInfo = hashMapOf(
                    "Age" to "29",
                    "Education" to "BBA in Marketing",
                    "Skills" to "SEO, Content Marketing, Social Media Management",
                    "LinkedIn" to "https://www.linkedin.com/in/charliedavis",
                    "Portfolio" to "https://charliedavis-portfolio.com"
                )
            )
        )
    }
}