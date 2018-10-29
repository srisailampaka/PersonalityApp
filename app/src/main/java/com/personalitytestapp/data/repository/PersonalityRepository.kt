package com.personalitytestapp.data.repository

import com.personalitytestapp.data.model.response.QuestionDetails
import com.personalitytestapp.data.model.request.AnswerList
import com.personalitytestapp.data.model.response.AnswerResponse
import io.reactivex.Single
/**
 * interface for PersonalityRepository
 */
interface PersonalityRepository {
    fun getPersonalityTestDetails(): Single<QuestionDetails>
    fun submitTheDetails(answers: AnswerList): Single<AnswerResponse>
}