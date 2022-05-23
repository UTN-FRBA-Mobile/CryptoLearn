package com.mobile.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobile.test.model.Chapter

class ChaptersAdapter(private val chapters: MutableList<Chapter>) :
    RecyclerView.Adapter<ChaptersAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_listitem_chapter, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val image = chapters[position].image
//        holder.view.findViewById<TextView>(R.id.chapter_image).background = createFromPath("res/drawable/chapter_3.png")
        holder.view.findViewById<TextView>(R.id.chapter_name).text = chapters[position].name
        // TODO: Send specific information to each chapter with navigation
        holder.view.findViewById<Button>(R.id.chapter_image).setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_homeFragment_to_chapterFragment)
        }
    }

/*    override fun getItemViewType(position: Int): Int {
        return if (myDataset[position].IsCategory)
            VIEWTYPE_CATEGORY
        else
            VIEWTYPE_MOVIE
    }*/

    override fun getItemCount() = chapters.size
}
