package com.mobile.test

import android.graphics.drawable.Drawable
import android.graphics.drawable.Drawable.createFromPath
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.mobile.test.model.Chapter
import com.mobile.test.model.Level

class ChaptersAdapter(private val chapters: MutableList<Chapter>):
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
    }

/*    override fun getItemViewType(position: Int): Int {
        return if (myDataset[position].IsCategory)
            VIEWTYPE_CATEGORY
        else
            VIEWTYPE_MOVIE
    }*/

    override fun getItemCount() = chapters.size
}
