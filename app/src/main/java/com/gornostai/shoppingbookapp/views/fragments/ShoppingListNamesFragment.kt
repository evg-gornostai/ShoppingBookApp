package com.gornostai.shoppingbookapp.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gornostai.shoppingbookapp.App
import com.gornostai.shoppingbookapp.MainViewModel
import com.gornostai.shoppingbookapp.databinding.FragmentShoppingListNamesBinding
import com.gornostai.shoppingbookapp.model.entities.ShoppingListNameItem
import com.gornostai.shoppingbookapp.utils.TimeManager
import com.gornostai.shoppingbookapp.utils.ViewModelFactory
import com.gornostai.shoppingbookapp.views.activities.ShoppingListActivity
import com.gornostai.shoppingbookapp.views.adapters.ShoppingListNamesAdapter
import com.gornostai.shoppingbookapp.views.dialogs.DeleteDialog
import com.gornostai.shoppingbookapp.views.dialogs.NewListDialog

class ShoppingListNamesFragment: BaseFragment() ,ShoppingListNamesAdapter.Listener {

    private lateinit var binding: FragmentShoppingListNamesBinding

    private lateinit var nameAdapter: ShoppingListNamesAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        ViewModelFactory((context?.applicationContext as App).dataBase)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListNamesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener{
            override fun onClick(name: String) {
                val shoppingListName = ShoppingListNameItem(
                    null,
                    name,
                    TimeManager.getCurrentTime(),
                    0,
                    0,
                    ""
                )
                mainViewModel.insertShoppingListName(shoppingListName)
            }
        })
    }

    private fun initRcView() = with(binding){
        rv.layoutManager = LinearLayoutManager(activity)
        nameAdapter = ShoppingListNamesAdapter(this@ShoppingListNamesFragment)
        rv.adapter = nameAdapter
    }

    private fun observer(){
        mainViewModel.allShopListNames.observe(viewLifecycleOwner){
            nameAdapter.submitList(it)
            binding.tvEmpty.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    override fun deleteItem(id: Int) {
        DeleteDialog.showDialog(context as AppCompatActivity,object : DeleteDialog.Listener{
            override fun onClick() {
                mainViewModel.deleteShopList(id)
            }
        })
    }

    override fun onClickItem(shopListNameItem: ShoppingListNameItem) {
        val intent = Intent(activity,ShoppingListActivity::class.java).apply {
            putExtra(ShoppingListActivity.SHOP_LIST_NAME, shopListNameItem)
        }
        startActivity(intent)
    }

    override fun editItem(shopListNameItem: ShoppingListNameItem) {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener{
            override fun onClick(name: String) {
                mainViewModel.updateListName(shopListNameItem.copy(name = name))
            }
        }, shopListNameItem.name)
    }

    companion object{
        fun newInstance() = ShoppingListNamesFragment()
    }

}