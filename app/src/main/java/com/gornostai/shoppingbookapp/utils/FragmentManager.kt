package com.gornostai.shoppingbookapp.utils

import androidx.appcompat.app.AppCompatActivity
import com.gornostai.shoppingbookapp.R
import com.gornostai.shoppingbookapp.views.fragments.BaseFragment

object FragmentManager {
    var currentFragment: BaseFragment? = null

    fun setFragment(newFragment: BaseFragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.main_fragment_container, newFragment)
        transaction.commit()
        currentFragment = newFragment
    }
}