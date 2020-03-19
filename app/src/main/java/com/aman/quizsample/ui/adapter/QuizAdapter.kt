package com.aman.quizsample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aman.quizsample.R
import com.aman.quizsample.databinding.QuizDataBinding
import com.aman.quizsample.room.entity.Quiz

class QuizAdapter(var quizList: List<Quiz>): RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        return QuizViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_single_item, parent, false),
            quizList
        )
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(quizList[position], position)
    }

    inner class QuizViewHolder(private val quizDataBinding: QuizDataBinding,
                               quizList: List<Quiz>)
        : RecyclerView.ViewHolder(quizDataBinding.root) {

        fun bind(quiz: Quiz, position: Int) {
            quizDataBinding.tvFirstAnswer.text = quiz.answers[0]
            quizDataBinding.tvSecondAnswer.text = quiz.answers[1]
            quizDataBinding.tvThirdAnswer.text = quiz.answers[2]
            quizDataBinding.tvFourthAnswer.text = quiz.answers[3]

            quizDataBinding.position = position.inc().toString() +" of "+ quizList.size
        }
    }

}
