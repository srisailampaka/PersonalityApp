package com.personalitytestapp.ui.personality

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.personalitytestapp.R
import com.personalitytestapp.data.model.response.Question
import com.personalitytestapp.data.model.response.QuestionDetails
import com.personalitytestapp.data.model.request.AnswerList
import com.personalitytestapp.data.model.response.AnswerResponse
import com.personalitytestapp.ui.adapter.QuestionAdapter
import com.personalitytestapp.utils.CommonUtil
import com.personalitytestapp.utils.ProgressDialog
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.personality_fragment.*
import java.util.*
import javax.inject.Inject

private val TAG = PersonalityFragment::class.java.name


/**
 * Fragment class for Personality details like questions list or submit the answers
 */
class PersonalityFragment : Fragment(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PersonalityViewModel
    var questionList = ArrayList<Question>()


    companion object {
        fun newInstance() = PersonalityFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.personality_fragment, container, false)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PersonalityViewModel::class.java)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
        if (CommonUtil.isNetworkStatusAvailable(context!!)) {
            viewModel.getQuestionsList()
            ProgressDialog.showProgressDialog(this!!.context!!)
        } else {
            CommonUtil.showAlertDialog(this!!.context!!, "", resources.getString(R.string.network_error), DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()

            })
        }

        setAdapter()
        recyclerview.layoutManager = LinearLayoutManager(activity)
        submit.setOnClickListener(this)
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.submit -> submitTheDetails()
        }
    }

    // method for submit the answers
    private fun submitTheDetails() {
        if (validation()) {
            if (CommonUtil.isNetworkStatusAvailable(context!!)) {
                ProgressDialog.showProgressDialog(this!!.context!!)
                val answerList = (recyclerview.adapter as QuestionAdapter).getAnswerList()
                for (i in 0 until answerList.size) {
                    answerList.get(i).username = user_edittext.text.toString()
                }
                val answers = AnswerList(answerList)

                viewModel.postTheAnswers(answers)
            } else {
                CommonUtil.showAlertDialog(this!!.context!!, "", resources.getString(R.string.not_answer), DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()

                })
            }

        }
    }

    // validation for username field and questions
    private fun validation(): Boolean {
        var status = true
        if (TextUtils.isEmpty(user_edittext.text.toString())) {
            user_edittext.setError(resources.getString(R.string.username_error))
            status = false
        } else if (!radioGroupValidation()) {
            CommonUtil.showAlertDialog(this!!.context!!, "", resources.getString(R.string.not_answer), DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()

            })
            status = false
        }
        return status;
    }

    private fun radioGroupValidation(): Boolean {
        var status = true
        val answerList = (recyclerview.adapter as QuestionAdapter).getAnswerList()
        for (i in 0 until answerList.size) {
            if (TextUtils.isEmpty(answerList.get(i).answer)) {
                status = false
            }
        }
        return status
    }

    private val stateQuestionObserver = Observer<QuestionDetails> {
        ProgressDialog.dismissProgressDialog()
        Log.d(TAG, "data -> ${it!!.questionsList.size} ")
        questionList = it.questionsList as ArrayList<Question>
        setAdapter()

    }

    private val stateAnswerObserver = Observer<AnswerResponse> {
        ProgressDialog.dismissProgressDialog()
        Log.d(TAG, "data -> ${it as AnswerResponse} ")

        CommonUtil.showAlertDialog(this!!.context!!, it.status, it.message, DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()

        })
        setAdapter()
        user_edittext.setText("")
    }

    private val errorStateObserver = Observer<String> {
        ProgressDialog.dismissProgressDialog()
        Toast.makeText(context, it, Toast.LENGTH_LONG).show()

    }


    private fun setAdapter() {
        recyclerview.adapter = QuestionAdapter(this.questionList)
    }

    private fun observerViewModel() {
        viewModel.stateQuestionsData.observe(this, stateQuestionObserver)
        viewModel.stateAnswersData.observe(this, stateAnswerObserver)
        viewModel.errorData.observe(this, errorStateObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stateQuestionsData.removeObserver(stateQuestionObserver)
        viewModel.stateAnswersData.removeObserver(stateAnswerObserver)
        viewModel.errorData.removeObserver(errorStateObserver)
    }
}