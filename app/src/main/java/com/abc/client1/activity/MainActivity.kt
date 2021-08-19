package com.abc.client1.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.abc.client1.R
import com.abc.client1.data.models.MenuModel
import com.abc.client1.databinding.ActivityMainBinding
import com.abc.client1.utils.ExpandableListAdapter
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), DrawerLocker, SetupActionBar,
    NavigationView.OnNavigationItemSelectedListener {


    companion object{
        const val DASHBOARD = 1
        const val POLICY = 2
        const val ASSESSMENT = 3
        const val QUOTE = 4
        const val ROAD = 5
        const val MOTOR = 6
        const val LIFE = 7
        const val DOMESTIC = 8
        const val HEALTH = 9
        const val INVESTMENT = 10
        const val MARINE = 11
        const val PURCHASE = -1

    }

    private lateinit var binding: ActivityMainBinding
    var expandableListAdapter: ExpandableListAdapter? = null
    var expandableListView: ExpandableListView? = null
    var headerList: MutableList<MenuModel> = mutableListOf()
    var childList: HashMap<MenuModel, MutableList<MenuModel?>?> = HashMap()
    private var navController: NavController? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var active = R.id.dashboardFragment

    private lateinit var listener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        expandableListView = binding.expandableListView
        prepareMenuData()
        populateExpandableList()

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

    private fun prepareMenuData() {
        var menuModel = MenuModel(
            "Dashboard",
            true,
            false,
            DASHBOARD
        )

        headerList.add(menuModel)

        if (!menuModel.hasChildren) {
            childList[menuModel] = null
        }

        menuModel = MenuModel(
            "My Policy Status",
            true,
            false,
            POLICY
        )

        headerList.add(menuModel)

        if (!menuModel.hasChildren) {
            childList[menuModel] = null
        }

        menuModel = MenuModel(
            "Assessment",
            true,
            false,
            ASSESSMENT
        )

        headerList.add(menuModel)

        if (!menuModel.hasChildren) {
            childList[menuModel] = null
        }

        menuModel = MenuModel(
            "Request Quote",
            true,
            false,
            QUOTE
        )

        headerList.add(menuModel)

        if (!menuModel.hasChildren) {
            childList[menuModel] = null
        }

        menuModel = MenuModel(
            "Road Side Assistance",
            true,
            false,
            ROAD
        )

        headerList.add(menuModel)

        if (!menuModel.hasChildren) {
            childList[menuModel] = null
        }

        createMenuWithChild()
    }

    private fun createMenuWithChild() {
        val menuModel = MenuModel("Purchase Insurance", true, true, PURCHASE) //Menu of Java Tutorials

        headerList.add(menuModel)
        val childModelsList: MutableList<MenuModel?> = ArrayList()
        var childModel = MenuModel(
            "Motor Insurance",
            false,
            false,
            MOTOR
        )
        childModelsList.add(childModel)

        childModel = MenuModel(
            "Life Insurance",
            true,
            false,
            LIFE
        )
        childModelsList.add(childModel)

        childModel = MenuModel(
            "Domestic Insurance",
            false,
            false,
            DOMESTIC
        )
        childModelsList.add(childModel)

        childModel = MenuModel(
            "Health Insurance",
            false,
            false,
            HEALTH
        )
        childModelsList.add(childModel)


        childModel = MenuModel(
            "Investment Insurance",
            false,
            false,
            INVESTMENT
        )
        childModelsList.add(childModel)

        childModel = MenuModel(
            "Marine Insurance",
            false,
            false,
            MARINE
        )
        childModelsList.add(childModel)



        if (menuModel.hasChildren) {
            childList[menuModel] = childModelsList
        }
    }

    private fun populateExpandableList() {

        expandableListAdapter = ExpandableListAdapter(this, headerList, childList)
        expandableListView!!.setAdapter(expandableListAdapter)

        expandableListView!!.setOnGroupClickListener { _, _, groupPosition, _ ->
            if (headerList[groupPosition].isGroup) {
                val model = headerList[groupPosition]
                if (!model.hasChildren) {
                    navigate(model.navigateCode)
                }
            }
            false
        }

        expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            if (childList[headerList[groupPosition]] != null) {
                val model = childList[headerList[groupPosition]]!![childPosition]
                if (model != null) {
                    navigate(model.navigateCode)
                }
            }
            false
        }

    }

    private fun navigate(code: Int) {
        when(code){
            DASHBOARD -> {
                findNavController(R.id.nav_host_fragment_container).navigate(R.id.dashboardFragment)
                onBackPressed()
            }
            ROAD -> {
                findNavController(R.id.nav_host_fragment_container).navigate(R.id.roadSideFragment)
                onBackPressed()
            }
            POLICY -> {
                navigateBrowser("http://fidelityshield.com/account/")
                onBackPressed()
            }
            ASSESSMENT -> {
                navigateBrowser("https://abcautovaluersltd.com")
                onBackPressed()
            }
            MOTOR -> {
                findNavController(R.id.nav_host_fragment_container).navigate(R.id.motorInsuranceFragment)
                onBackPressed()
            }
            DOMESTIC -> {
                navigateBrowser("https://fidelityshield.com/product/domestic-package/")
                onBackPressed()
            }
            INVESTMENT -> {
                navigateBrowser("https://fidelityshield.com/products/bonds/")
                onBackPressed()
            }
            MARINE -> {
                navigateBrowser("https://fidelityshield.com/product/marine-insurance/")
                onBackPressed()
            }
            QUOTE -> {
                navigateBrowser("https://fidelityshield.com/product/domestic-package/#Enquire")
                onBackPressed()
            }

        }
    }

    private fun navigateBrowser(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
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
        when (item.itemId) {
            R.id.dashboardFragment -> {
                findNavController(R.id.nav_host_fragment_container).navigate(R.id.dashboardFragment)
            }
            R.id.roadSideFragment -> {
                findNavController(R.id.nav_host_fragment_container).navigate(R.id.roadSideFragment)
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
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}