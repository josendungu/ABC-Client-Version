package com.android.abc.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.android.abc.R
import com.android.abc.databinding.ActivityMainBinding
import com.android.abc.fragments.DashboardFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), DrawerLocker, SetupActionBar,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private var navController: NavController? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var active = R.id.dashboardFragment

    private lateinit var listener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment_container)
        appBarConfiguration = AppBarConfiguration(navController!!.graph, binding.drawerLayout)
        setupDrawerLayout()

        val window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue)


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
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun unlockDrawer() {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_container)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupDrawerLayout() {
        binding.navView.setupWithNavController(navController!!)
        binding.navView.setNavigationItemSelectedListener(this)
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
                findNavController( R.id.nav_host_fragment_container).navigate(R.id.dashboardFragment)
            }
            R.id.roadSideFragment -> {
                findNavController( R.id.nav_host_fragment_container).navigate(R.id.roadSideFragment)
            }
            R.id.nav_policy_status -> {
                val url = "http://fidelityshield.com/account/"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
//            R.id.nav_covers -> {
//                val url = "http://fidelityshield.com/products/personal-solutions/"
//                val i = Intent(Intent.ACTION_VIEW)
//                i.data = Uri.parse(url)
//                startActivity(i)
//            }
            R.id.assessment -> {
                val url = "https://abcautovaluersltd.com"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
            R.id.quote -> {
                Toast.makeText(this, "Coming soon", Toast.LENGTH_LONG).show()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }




}