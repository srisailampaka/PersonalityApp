package com.personalitytestapp.data

import com.personalitytestapp.data.model.response.QuestionDetails
import com.personalitytestapp.data.model.request.AnswerList
import com.personalitytestapp.data.model.response.AnswerResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface PersonalityService {
    @Headers("Content-type: application/json")
    @GET("questions")
    fun getPersonalityQuestionsList(): Single<QuestionDetails>


    @Headers("Content-type: application/json")
    @POST("answers")
    fun submitTheDetails(@Body response: AnswerList): Single< AnswerResponse>
}