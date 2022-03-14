package com.gornostai.shoppingbookapp.views.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gornostai.shoppingbookapp.R
import com.gornostai.shoppingbookapp.databinding.ItemShopingListBinding
import com.gornostai.shoppingbookapp.model.entities.ShoppingListItem

class ShoppingListItemsAdapter(private val listener: Listener) :
    ListAdapter<ShoppingListItem, ShoppingListItemsAdapter.ItemHolder>(
        ItemComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.createShoppingItem(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setItemData(getItem(position),listener)
    }


    class ItemHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun setItemData(shoppingListItem: ShoppingListItem, listener: Listener) {
            val binding = ItemShopingListBinding.bind(view)
            binding.apply {
                tvTitle.text = shoppingListItem.name
                tvDescription.text = shoppingListItem.itemInfo
                tvDescription.visibility = infoVisibility(shoppingListItem)
                chbox.isChecked = shoppingListItem.itemChecked
                setPaintFlagAndColor(binding)
                chbox.setOnClickListener {
                    listener.onClickItem(shoppingListItem.copy(itemChecked = chbox.isChecked), CHECK)
                }
                imBtnEdit.setOnClickListener {
                    listener.onClickItem(shoppingListItem, EDIT)
                }
            }
        }

        private fun infoVisibility(shoppingListItem: ShoppingListItem): Int{
            return if(shoppingListItem.itemInfo.isEmpty()){
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        private fun setPaintFlagAndColor(binding: ItemShopingListBinding){
            binding.apply {
                if (chbox.isChecked){
                    tvTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvDescription.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                    tvDescription.setTextColor(ContextCompat.getColor(binding.root.context, R.color.gray))
                    container.alpha = 0.9f
                } else {
                    tvTitle.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvDescription.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvTitle.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                    tvDescription.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                    container.alpha = 1.0f
                }
            }
        }

        companion object {
            fun createShoppingItem(parent: ViewGroup): ItemHolder {
                val binding: ItemShopingListBinding = ItemShopingListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ItemHolder(binding.root)
            }
        }

    }

    class ItemComparator() : DiffUtil.ItemCallback<ShoppingListItem>() {
        override fun areItemsTheSame(
            oldItem: ShoppingListItem,
            newItem: ShoppingListItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShoppingListItem,
            newItem: ShoppingListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun onClickItem(shoppingListItem: ShoppingListItem, state: Int)
    }

    companion object {
        const val EDIT = 0
        const val CHECK = 1
    }

}









