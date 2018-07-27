package com.shenghaiyang.oksign.library

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class LibraryAdapter(val libraries: List<Library>) : RecyclerView.Adapter<LibraryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LibraryViewHolder.create(parent)

    override fun getItemCount() = libraries.size

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(libraries[position])
    }

}
