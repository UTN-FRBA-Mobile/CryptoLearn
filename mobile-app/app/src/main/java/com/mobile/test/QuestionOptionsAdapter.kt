package com.mobile.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.test.model.Level
import com.mobile.test.model.Question

class QuestionOptionsAdapter(private val options: MutableList<String>) :
    RecyclerView.Adapter<QuestionOptionsAdapter.MyViewHolder>() {
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_question_option, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val option = options[position]
        val button = holder.view.findViewById<Button>(R.id.questionOptionButton)
        button.text = option
//        button.setOnClickListener()
    }

    override fun getItemCount() = options.size
}
