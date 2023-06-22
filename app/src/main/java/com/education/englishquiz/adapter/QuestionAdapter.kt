package com.education.englishquiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.education.englishquiz.R
import com.education.englishquiz.model.Questions

class QuestionAdapter : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    private var questionList: List<Questions> = emptyList()

    fun setQuestions(questions: List<Questions>) {
        questionList = questions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.summery_items, parent, false)
        return QuestionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questionList[position]
        holder.bind(question)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val topicText: TextView = itemView.findViewById(R.id.tv_RecInstruction)
        private val questionText: TextView = itemView.findViewById(R.id.tv_RecQuestion)
        private val answerText: TextView = itemView.findViewById(R.id.tv_correctOpt)

        fun bind(question: Questions) {
            val s1 = question.topic
            val topic = "In the given sentence find $s1"
            val s2 = question.ques
            val s3 = question.correct_opt
            var ans =""
            when(s3){
                1-> ans = question.opt1
                2-> ans = question.opt2
                3-> ans = question.opt3
                4-> ans = question.opt4
            }
            val s4 ="Answer : $ans"
            topicText.text = topic
            questionText.text = s2
            answerText.text = s4
        }
    }
}
