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
    "chapter_1" to R.drawable.chapter_1,
    "chapter_2" to R.drawable.chapter_2,
    "chapter_3" to R.drawable.chapter_3
)


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
        val image = chapters[position].image
        // holder.view.findViewById<TextView>(R.id.chapter_image).setBackgroundResource(imagesMap[image]!!)
        holder.view.findViewById<TextView>(R.id.chapter_name).text = chapters[position].name
        // TODO: Send specific information to each chapter with navigation
        holder.view.findViewById<Button>(R.id.chapter_image).setOnClickListener { view ->
            // Para mi aca deberiamos pasarle un ID de chapter y que el componente se ocupe de buscar la data del back en el onCreate
            val bundle = bundleOf("chapterData" to chapters[position])
            view.findNavController().navigate(R.id.action_homeFragment_to_chapterFragment, bundle)
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
