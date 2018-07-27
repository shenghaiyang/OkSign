package com.shenghaiyang.oksign.main

import android.content.Intent
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shenghaiyang.oksign.R
import kotlinx.android.synthetic.main.signature_item.view.*

class SignatureViewHolder(itemView: View) : ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup) = SignatureViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.signature_item, parent, false)).also {
        }
    }

    fun bind(signature: String) {
        itemView.signature_view.text = signature
        itemView.share_view.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, signature)
            intent.type = "text/plain"
            it.context.startActivity(Intent.createChooser(intent, "Share Signature"))
        }
    }

}
