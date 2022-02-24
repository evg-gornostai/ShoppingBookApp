package com.gornostai.shoppingbookapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gornostai.shoppingbookapp.databinding.FragmentShoppingListNamesBinding

class ShoppingListNamesFragment: BaseFragment() {

    private lateinit var binding: FragmentShoppingListNamesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListNamesBinding.inflate(inflater)
        return binding.root
    }

    override fun onClickNew() {

    }

    companion object{
        fun newInstance() = ShoppingListNamesFragment()
    }

}