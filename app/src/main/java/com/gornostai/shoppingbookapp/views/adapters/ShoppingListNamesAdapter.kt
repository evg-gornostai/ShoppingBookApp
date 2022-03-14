package com.gornostai.shoppingbookapp.views.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gornostai.shoppingbookapp.R
import com.gornostai.shoppingbookapp.databinding.ItemListNameBinding
import com.gornostai.shoppingbookapp.model.entities.ShoppingListNameItem

class ShoppingListNamesAdapter(private val listener: Listener) : ListAdapter<ShoppingListNameItem, ShoppingListNamesAdapter.ItemHolder>(
    ItemComparator()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemListNameBinding.bind(view)

        fun setData(shopListNameItem: ShoppingListNameItem, listener: Listener) = with(binding) {
            tvListName.text = shopListNameItem.name
            tvTime.text = shopListNameItem.time
            pBar.max = shopListNameItem.allItemCounter
            pBar.progress = shopListNameItem.checkedItemCounter
            val colorState = ColorStateList.valueOf(getProgressColorState(shopListNameItem,binding.root.context))
            pBar.progressTintList = colorState
            val counterText = "${shopListNameItem.checkedItemCounter}/${shopListNameItem.allItemCounter}"
            tvCounter.text = counterText
            itemView.setOnClickListener {
                listener.onClickItem(shopListNameItem)
            }
            imBtnEdit.setOnClickListener {
                listener.editItem(shopListNameItem)
            }
            imBtnDelete.setOnClickListener {
                listener.deleteItem(shopListNameItem.id!!)
            }
        }

        private fun getProgressColorState(item: ShoppingListNameItem, context: Context): Int{
            return if (item.allItemCounter == item.checkedItemCounter){
                ContextCompat.getColor(context, R.color.green)
            } else {
                ContextCompat.getColor(context,R.color.red)
            }
        }

        companion object{
            fun create(parent: ViewGroup): ItemHolder{
                return ItemHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_list_name, parent, false)
                )
            }
        }

    }

    class ItemComparator : DiffUtil.ItemCallback<ShoppingListNameItem>() {
        override fun areItemsTheSame(oldItem: ShoppingListNameItem, newItem: ShoppingListNameItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingListNameItem, newItem: ShoppingListNameItem): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun deleteItem(id: Int)
        fun editItem(shopListNameItem: ShoppingListNameItem)
        fun onClickItem(shopListNameItem: ShoppingListNameItem)
    }

}