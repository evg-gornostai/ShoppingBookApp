package com.gornostai.shoppingbookapp.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gornostai.shoppingbookapp.App
import com.gornostai.shoppingbookapp.MainViewModel
import com.gornostai.shoppingbookapp.R
import com.gornostai.shoppingbookapp.databinding.ActivityShoppingListBinding
import com.gornostai.shoppingbookapp.model.entities.ShoppingListItem
import com.gornostai.shoppingbookapp.model.entities.ShoppingListNameItem
import com.gornostai.shoppingbookapp.utils.ShareHelper
import com.gornostai.shoppingbookapp.utils.ViewModelFactory
import com.gornostai.shoppingbookapp.views.adapters.ShoppingListItemsAdapter
import com.gornostai.shoppingbookapp.views.dialogs.EditListItemDialog

class ShoppingListActivity : AppCompatActivity(), ShoppingListItemsAdapter.Listener {

    private lateinit var binding: ActivityShoppingListBinding
    private var shopListNameItem: ShoppingListNameItem? = null
    private var adapter: ShoppingListItemsAdapter? = null

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory((applicationContext as App).dataBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.newItemLayout.btnSave.setOnClickListener {
            addNewShopItem()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_shopping_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clear_list -> {
                mainViewModel.clearShopList(shopListNameItem?.id!!)
            }
            R.id.delete_list -> {
                mainViewModel.deleteShopList(shopListNameItem?.id!!)
                finish()
            }
            R.id.share_list -> {
                startActivity(
                    Intent.createChooser(
                        ShareHelper.shareShopList(adapter?.currentList!!, shopListNameItem?.name!!),
                        "Share by"
                    )
                )
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    override fun onClickItem(shoppingListItem: ShoppingListItem, state: Int) {
        when (state) {
            ShoppingListItemsAdapter.CHECK -> {
                mainViewModel.updateListItem(shoppingListItem)
            }
            ShoppingListItemsAdapter.EDIT -> {
                editShoppingListItem(shoppingListItem)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        saveItemCount()
    }

    private fun saveItemCount() {
        var checkedItemCounter = 0
        adapter?.currentList?.forEach {
            if (it.itemChecked) checkedItemCounter++
        }
        val tempShopListNameItem = shopListNameItem?.copy(
            allItemCounter = adapter?.itemCount!!,
            checkedItemCounter = checkedItemCounter
        )
        mainViewModel.updateListName(tempShopListNameItem!!)
    }

    private fun addNewShopItem() {
        if (binding.newItemLayout.edNewShopItem.text.toString().isEmpty()) {
            return
        } else {
            val item = ShoppingListItem(
                null,
                binding.newItemLayout.edNewShopItem.text.toString(),
                "",
                false,
                shopListNameItem?.id!!
            )
            mainViewModel.insertShopListItem(item)
            binding.newItemLayout.edNewShopItem.setText("")
        }
    }

    private fun editShoppingListItem(item: ShoppingListItem) {
        EditListItemDialog.showDialog(this, item, object : EditListItemDialog.Listener {
            override fun onClick(item: ShoppingListItem) {
                mainViewModel.updateListItem(item)
            }
        })
    }

    private fun init() {
        shopListNameItem = intent.getSerializableExtra(SHOP_LIST_NAME) as ShoppingListNameItem
        binding.apply {
            rcVew.layoutManager = LinearLayoutManager(this@ShoppingListActivity)
            adapter = ShoppingListItemsAdapter(this@ShoppingListActivity)
            rcVew.adapter = adapter
        }
        mainViewModel.getAllItemsFromList(shopListNameItem?.id!!).observe(this) {
            adapter?.submitList(it)
            binding.tvEmpty.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    companion object {
        const val SHOP_LIST_NAME = "shop_list_name"
    }

}