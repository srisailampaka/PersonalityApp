package com.personalitytestapp.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.personalitytestapp.R
import com.personalitytestapp.data.model.response.Question
import com.personalitytestapp.data.model.request.Answer
import kotlinx.android.synthetic.main.item_question.view.*
import java.util.*

/**
 * Adapter  class for question list
 */
class QuestionAdapter(questions: MutableList<Question>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
    private val questionList: MutableList<Question>
    var answerList = ArrayList<Answer>()

    init {
        questionList = questions
        for (i in 0 until questionList.size) {
            val answer = Answer(questionList.get(i).id, "", "");
            answerList.add(answer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_question, parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = questionList.get(position)
        holder.question.text = item.question
        holder.options.tag = position
        holder.options.removeAllViews()
        var optionList = item.options.split(",")
        for (i in 0 until optionList.size) {
            var radioButton = RadioButton(holder.options.context)
            radioButton.text = optionList[i]
            holder.options.addView(radioButton)
        }

        holder.options.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            val selectedId = radioGroup.getCheckedRadioButtonId()
            val radioButton = radioGroup.findViewById(selectedId) as RadioButton
            answerList.get(holder.options.tag as Int).answer = radioButton.text.toString()
        })
    }


    override fun getItemCount(): Int {
        return questionList.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var question = view.question
        var options = view.options_group
    }

    fun getAnswerList(): List<Answer> {
        return answerList
    }
}
