package com.gornostai.shoppingbookapp.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gornostai.shoppingbookapp.R
import com.gornostai.shoppingbookapp.databinding.ItemNoteBinding
import com.gornostai.shoppingbookapp.model.entities.NoteItem

class NoteAdapter(private val listener: Listener) : ListAdapter<NoteItem, NoteAdapter.ItemHolder>(
    ItemComparator()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position),listener)
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemNoteBinding.bind(view)

        fun setData(note: NoteItem, listener: Listener) = with(binding) {
            itemTitleTv.text = note.title
            itemDescriptionTv.text = note.description
            itemTimeTv.text = note.time
            itemView.setOnClickListener {
                listener.onClickItem(note)
            }
            itemNoteDeleteIb.setOnClickListener {
                note.id?.let { it1 -> listener.deleteItem(it1) }
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<NoteItem>() {
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun deleteItem(id: Int)
        fun onClickItem(note: NoteItem)
    }
}