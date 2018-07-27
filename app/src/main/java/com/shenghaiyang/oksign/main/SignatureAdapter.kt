package com.shenghaiyang.oksign.main

import android.support.v7.widget.RecyclerView.Adapter
import android.view.ViewGroup

class SignatureAdapter(val signatures: List<String>) : Adapter<SignatureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SignatureViewHolder.create(parent)

    override fun getItemCount() = signatures.size

    override fun onBindViewHolder(holder: SignatureViewHolder, position: Int) {
        holder.bind(signatures[position])
    }

}
