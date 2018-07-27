package com.shenghaiyang.oksign.library

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shenghaiyang.oksign.R
import kotlinx.android.synthetic.main.library_item.view.*

class LibraryViewHolder(itemView: View) : ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup) = LibraryViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.library_item, parent, false)).also {
        }
    }

    fun bind(library: Library) {
        itemView.name_view.text = library.name
        itemView.license_name_view.text = library.licenseName
        itemView.license_content_view.text = library.licenseContent
    }

}
