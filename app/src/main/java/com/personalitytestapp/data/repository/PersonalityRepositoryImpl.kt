package com.personalitytestapp.data.repository

import android.content.Context
import com.personalitytestapp.data.PersonalityService
import com.personalitytestapp.data.model.response.QuestionDetails
import com.personalitytestapp.data.model.request.AnswerList
import com.personalitytestapp.data.model.response.AnswerResponse
import io.reactivex.Single

/**
 * Implementation class for PersonalityRepositoryImpl
 */
class PersonalityRepositoryImpl(private val service: PersonalityService,
                                private val context: Context) : PersonalityRepository {
    override fun getPersonalityTestDetails(): Single<QuestionDetails> {
        return service.getPersonalityQuestionsList()
    }

    override fun submitTheDetails(answers: AnswerList): Single<AnswerResponse> {
        return service.submitTheDetails(answers)
    }
}