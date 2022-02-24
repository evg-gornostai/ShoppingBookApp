package com.gornostai.shoppingbookapp.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gornostai.shoppingbookapp.R
import com.gornostai.shoppingbookapp.databinding.ActivityMainBinding
import com.gornostai.shoppingbookapp.utils.FragmentManager
import com.gornostai.shoppingbookapp.views.fragments.NotesListFragment
import com.gornostai.shoppingbookapp.views.fragments.ShoppingListNamesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null){
            FragmentManager.setFragment(NotesListFragment.newInstance(), this)
        }
        setBottomNavListener()
    }

    private fun setBottomNavListener() {
        binding.mainBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_bottom_notes -> {
                    FragmentManager.setFragment(NotesListFragment.newInstance(), this)
                }
                R.id.menu_bottom_shop_list -> {
                    FragmentManager.setFragment(ShoppingListNamesFragment.newInstance(), this)
                }
                R.id.menu_bottom_new_item -> {
                    FragmentManager.currentFragment?.onClickNew()
                }
            }
            true
        }
    }

}