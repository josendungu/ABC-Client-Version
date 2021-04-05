package com.android.abc.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.android.abc.R
import com.android.abc.databinding.ActivityMainBinding
import com.android.abc.databinding.NavigationHeaderBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), DrawerLocker, SetupActionBar, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private var active = R.id.dashboardFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHostFragment =  supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        setupDrawerLayout()

        binding.navView.setNavigationItemSelectedListener(this)
    }


    override fun setup(toolbar: Toolbar, fragmentId: Int) {

        this.setSupportActionBar(toolbar)

        active = fragmentId

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }



    override fun lockDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    override fun unlockDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    private fun setupDrawerLayout() {

        binding.navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.dashboardFragment -> {
                navController.navigateUp()
                navController.navigate(R.id.dashboardFragment)
            }

            R.id.nav_policy_status -> {
                val url = "http://fidelityshield.com/account/"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }

            R.id.quote -> {
                Toast.makeText(this, "Coming soon", Toast.LENGTH_LONG).show()
            }

            R.id.nav_covers -> {
                val url = "http://fidelityshield.com/products/personal-solutions/"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
            R.id.assessment -> {
                val url = "https://abcautovaluersltd.com"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)

            }

            R.id.road_side -> {
                Toast.makeText(this, "Coming soon", Toast.LENGTH_LONG).show()
            }

            R.id.contact -> {
                navController.navigateUp()
                navController.navigate(R.id.contactFragment)
            }

        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }



}