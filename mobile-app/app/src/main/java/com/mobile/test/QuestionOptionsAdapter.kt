package com.mobile.test

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.test.model.Level
import com.mobile.test.model.Question
import kotlin.coroutines.coroutineContext

class QuestionOptionsAdapter(
    private val question: Question,
//    private val _options: MutableList<String>,
    private val onClickListener: OnClickListener?
) :
    RecyclerView.Adapter<QuestionOptionsAdapter.MyViewHolder>() {

    var button: Button? = null
    var options: MutableList<String> = question.options

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                onClickListener?.onClick(question.options[adapterPosition])
                Log.d("PRINT", "Button set color${button.toString()}")
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_question_option, parent, false)

        options = question.options
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val option = options[position]
        val b = holder.view.findViewById<Button>(R.id.questionOptionButton)
        b.text = option
        if (question.selectedAnswer == position) {
            if (question.isCorrect == true) {
                b.setTextColor(Color.rgb(0, 128, 0))
            } else if (question.isCorrect === null) {
                b.setTextColor(Color.BLACK)
            } else {
                b.setTextColor(Color.rgb(204, 0, 0))
            }
        } else {
            b.setTextColor(ContextCompat.getColor(holder.view.context, R.color.text))
        }
        button = b
    }

    override fun getItemCount() = options.size
}

class OnClickListener(val clickListener: (answer: String) -> Unit) {
    fun onClick(answer: String) = clickListener(answer)
}
