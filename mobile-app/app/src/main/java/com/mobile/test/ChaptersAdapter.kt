package com.mobile.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mobile.test.model.Chapter

private val imagesMap = mapOf<String, Int>(
    "chapter_1_done" to R.drawable.chapter_1_done,
    "chapter_2_done" to R.drawable.chapter_2_done,
    "chapter_3_done" to R.drawable.chapter_3_done,
    "chapter_1_block" to R.drawable.chapter_1_block,
    "chapter_2_block" to R.drawable.chapter_2_block,
    "chapter_3_block" to R.drawable.chapter_3_block,
    "chapter_1_in_progress" to R.drawable.chapter_1_in_progress,
    "chapter_2_in_progress" to R.drawable.chapter_2_in_progress,
    "chapter_3_in_progress" to R.drawable.chapter_3_in_progress,
)

class ChaptersAdapter(private val chapters: MutableList<Chapter>?) :
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
        if(!chapters.isNullOrEmpty()) {
            val chapter = chapters[position]
            holder.view.findViewById<TextView>(R.id.chapter_image).setBackgroundResource(imagesMap["${chapter.image}_${chapter.state}"]!!)
            holder.view.findViewById<TextView>(R.id.chapter_name).text = chapters[position].name
            if(chapter.state != "block"){
                holder.view.findViewById<Button>(R.id.chapter_image).setOnClickListener { view ->
                    val bundle = bundleOf("chapterData" to chapters[position])
                    view.findNavController().navigate(R.id.action_homeFragment_to_chapterFragment, bundle)
                }
            }
        }
    }

/*    override fun getItemViewType(position: Int): Int {
        return if (myDataset[position].IsCategory)
            VIEWTYPE_CATEGORY
        else
            VIEWTYPE_MOVIE
    }*/

    override fun getItemCount() = if (!chapters.isNullOrEmpty()) chapters.size else 0
}
