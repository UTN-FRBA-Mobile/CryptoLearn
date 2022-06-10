package com.mobile.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.test.model.Level

class LevelsAdapter(private val levels: List<Level>):
    RecyclerView.Adapter<LevelsAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(level: Level, position: Int) {
            itemView.findViewById<EditText>(R.id.level_name).setText("Nivel ${position+1}")
            val childMembersAdapter = ChaptersAdapter(level.chapters)
            itemView.findViewById<RecyclerView>(R.id.chapters_recycler_view).layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
            itemView.findViewById<RecyclerView>(R.id.chapters_recycler_view).adapter = childMembersAdapter
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_listitem_level, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(levels[position], position)
    }

/*    override fun getItemViewType(position: Int): Int {
        return if (myDataset[position].IsCategory)
            VIEWTYPE_CATEGORY
        else
            VIEWTYPE_MOVIE
    }*/

    override fun getItemCount() = levels.size
}
