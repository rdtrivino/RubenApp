package com.rubentrivino.rubenapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapter(
    private val items: MutableList<String>,
    private val onShare: (String) -> Unit,
    private val onEdit: (position: Int, current: String) -> Unit,
    private val onDelete: (position: Int) -> Unit
) : RecyclerView.Adapter<SimpleAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tv: TextView = v.findViewById(R.id.tvTask)
        val btnShare: ImageButton = v.findViewById(R.id.btnShare)
        val btnEdit: ImageButton = v.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = v.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(h: VH, position: Int) {
        val text = items[position]
        h.tv.text = text

        h.btnShare.setOnClickListener { onShare(text) }
        h.btnEdit.setOnClickListener { onEdit(h.adapterPosition, text) }
        h.btnDelete.setOnClickListener { onDelete(h.adapterPosition) }
    }

    override fun getItemCount(): Int = items.size
}
