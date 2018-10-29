package com.personalitytestapp.ui.personality

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.personalitytestapp.data.model.response.QuestionDetails
import com.personalitytestapp.data.model.request.AnswerList
import com.personalitytestapp.data.model.response.AnswerResponse
import com.personalitytestapp.data.repository.PersonalityRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
/**
 * ViewModel class for Personality
 */
class PersonalityViewModel @Inject constructor(private val repo: PersonalityRepository) : ViewModel() {

    val stateQuestionsData = MutableLiveData<QuestionDetails>()
    val stateAnswersData = MutableLiveData<AnswerResponse>()
    val errorData = MutableLiveData<String>()

    // Method for get the Question list

    fun getQuestionsList() {

        repo.getPersonalityTestDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onQuestionListResponse, this::onError)
    }

    // Method for submit the Question with answers
    fun postTheAnswers(answers: AnswerList) {

        repo.submitTheDetails(answers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSubmitListResponse, this::onError)
    }

    private fun onError(error: Throwable) {
        errorData.value = error.localizedMessage
    }

    private fun onQuestionListResponse(questionDetails: QuestionDetails) {
        stateQuestionsData.value = questionDetails

    }

    private fun onSubmitListResponse(response: AnswerResponse) {
        stateAnswersData.value = response

    }
}