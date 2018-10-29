package com.personalitytestapp.data.model.response

import com.personalitytestapp.data.model.response.Category
import com.personalitytestapp.data.model.response.Question

/**
 * Model class for QuestionDetails
 */
class QuestionDetails(
        val category: List<Category>,
        val questionsList: List<Question>)
