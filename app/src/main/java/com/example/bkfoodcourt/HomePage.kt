package com.example.bkfoodcourt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.bkfoodcourt.AccountFragment.Companion.newInstance
import com.example.bkfoodcourt.CartFragment.Companion.newInstance
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        title=resources.getString(R.string.menu)
        loadFragment(MenuFragment())

        navigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_cart-> {
                    title=resources.getString(R.string.cart)
                    loadFragment(CartFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_menu-> {
                    title=resources.getString(R.string.menu)
                    loadFragment(MenuFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_account-> {
                    title=resources.getString(R.string.account)
                    loadFragment(AccountFragment())
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false

        }
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    /*
    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_menu -> {
                val fragment = MenuFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_cart -> {
                val fragment = CartFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
            val fragment = AccountFragment()
            addFragment(fragment)
            return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = MenuFragment()
        addFragment(fragment)
    }
     */

    /*
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_menu -> {
                openFragment(MenuFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_cart -> {
                openFragment(CartFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account -> {
                openFragment(AccountFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigationView) as BottomNavigationView
        bottomNavigation.setOnNavigationItemReselectedListener {mOnNavigationItemSelectedListener}
    }
     */
}