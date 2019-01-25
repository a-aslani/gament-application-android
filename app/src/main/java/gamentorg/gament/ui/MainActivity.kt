package gamentorg.gament.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import gamentorg.gament.ControllerActivity
import gamentorg.gament.R
import gamentorg.gament.constants.Config
import gamentorg.gament.db.entities.Game
import gamentorg.gament.services.ApplicationService
import gamentorg.gament.vm.UserViewModel
import gamentorg.gament.vm.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.navigation_drawer.*
import kotlinx.android.synthetic.main.navigation_drawer_header.view.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var applicationService: ApplicationService

    @Inject
    lateinit var font: Typeface

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var picasso: Picasso

    private lateinit var navController: NavController

    private lateinit var userViewModel: UserViewModel

    private lateinit var appBarConfig: AppBarConfiguration

    private lateinit var navigationView: NavigationView

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)

        navigationView = findViewById(R.id.navigation_view)
        drawerLayout = findViewById(R.id.drawer_layout)

        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)

        if (applicationService.isLoggedIn()) {
            userViewModel.insertUser(sharedPreferences.getString(Config.API_TOKEN, "")!!)
        }

        //Set activity direction to RTL
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        main_collapsing_toolbar_layout.setCollapsedTitleTypeface(font)
        main_collapsing_toolbar_layout.setExpandedTitleTypeface(font)


        setupNavControllerAndConfig()

        setupNavigationDrawer()

        navControllerListener()
    }

    private fun navControllerListener() {

        navController.addOnDestinationChangedListener { navController, destination, arguments ->

            when (destination.id) {

                R.id.mainFragment -> setupMainUI(navController)
                R.id.gameFragment -> setupGameUI(navController, arguments)
                R.id.loginPageOneFragment -> setupLoginUI(navController)
            }
        }
    }
    private fun setExpandEnabled(enabled: Boolean) {

        main_appbar_layout.setExpanded(enabled, true)
        main_appbar_layout.isActivated = enabled

//        val params = main_collapsing_toolbar_layout.layoutParams as AppBarLayout.LayoutParams
//
//        if (enabled) {
//            params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED or AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
//        } else {
//            params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
//        }
//
//        main_collapsing_toolbar_layout.layoutParams = params

    }

    private fun setupLoginUI(controller: NavController) {

        main_collapsing_toolbar_layout.setupWithNavController(main_toolbar, controller, appBarConfig)
        main_toolbar.setupWithNavController(controller, appBarConfig)
        main_collapsing_toolbar_layout.collapsedTitleGravity = GravityCompat.START

        setExpandEnabled(false)

        game_tab_layout.visibility = View.GONE
//        main_fab.hide()

        main_fab.visibility = View.INVISIBLE

    }

    private fun setupGameUI(controller: NavController, arguments: Bundle?) {

        main_collapsing_toolbar_layout.setupWithNavController(main_toolbar, controller, appBarConfig)
        main_toolbar.setupWithNavController(controller, appBarConfig)
        main_collapsing_toolbar_layout.collapsedTitleGravity = GravityCompat.END

        setupGameUiVisibility()

        setExpandEnabled(true)

        if (arguments != null) {
            setupGameUiData(arguments)
        }

    }

    private fun setupGameUiData(arguments: Bundle?) {

        val game: Game? = arguments?.getParcelable(getString(R.string.game_extras))

        game_img_game_pc.setImageResource(R.drawable.ic_computer)
        game_img_game_ps.setImageResource(R.drawable.ic_ps)
        game_img_game_mobile.setImageResource(R.drawable.ic_phone)
        game_img_game_xbox.setImageResource(R.drawable.ic_xbox)

        if (game != null) {

            main_collapsing_toolbar_layout.title = game.name!!.toUpperCase()

            if (game.pc == true) {
                game_img_game_pc.setImageResource(R.drawable.ic_computer_active)
            }
            if (game.ps == true) {
                game_img_game_ps.setImageResource(R.drawable.ic_ps_active)
            }
            if (game.mobile == true) {
                game_img_game_mobile.setImageResource(R.drawable.ic_phone_active)
            }
            if (game.xbox == true) {
                game_img_game_xbox.setImageResource(R.drawable.ic_xbox_active)
            }

            picasso.load(Config.SERVER_ADDRESS + "/" + game.image).into(main_banner_image)
        }

    }

    private fun setupGameUiVisibility() {

        game_tab_layout.visibility = View.VISIBLE
//        main_fab.show()
        main_fab.visibility = View.VISIBLE
    }

    private fun setupMainUI(controller: NavController) {

        main_collapsing_toolbar_layout.setupWithNavController(main_toolbar, controller, appBarConfig)
        main_toolbar.setupWithNavController(controller, appBarConfig)
        main_collapsing_toolbar_layout.collapsedTitleGravity = GravityCompat.START

        setupMainUiVisibility()

        setExpandEnabled(false)

    }


    private fun setupMainUiVisibility() {
        game_tab_layout.visibility = View.GONE
//        main_fab.show()
        main_fab.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        setupFab()
        setupNavigationDrawerData()
    }

    fun refreshUi() {
        onResume()
    }

    private fun setupNavControllerAndConfig() {
        navController = findNavController(R.id.main_nav_host_fragment)
        appBarConfig = AppBarConfiguration(navController.graph, drawerLayout)
        navigationView.setupWithNavController(navController)
        main_collapsing_toolbar_layout.setupWithNavController(main_toolbar, navController, appBarConfig)
        main_toolbar.setupWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp() : Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }


    private fun setupFab() {

        if (!applicationService.isLoggedIn()) {
            main_fab.setImageResource(R.drawable.ic_person_add)
            main_fab.setOnClickListener {
                navController.navigate(R.id.action_global_loginPageOneFragment)
            }
        } else {
            main_fab.setImageResource(R.drawable.ic_game_pad)
            main_fab.setOnClickListener {
                startActivity(Intent(this, ControllerActivity::class.java))

            }
        }
    }

    private fun setupNavigationDrawer() {
        val toggle =
            ActionBarDrawerToggle(this, drawerLayout, main_toolbar,
                R.string.open_drawer,
                R.string.close_drawer
            )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            when (it.itemId) {

            }

            drawerLayout.closeDrawer(GravityCompat.START, true)
            true
        }

    }

    fun logoutOnClick(v: View) {
        sharedPreferences.edit().remove(Config.API_TOKEN).apply()
        drawerLayout.closeDrawer(GravityCompat.START)
        onResume()
    }

    @SuppressLint("SetTextI18n")
    private fun setupNavigationDrawerData() {

        val navCount: Int = navigation_view.headerCount

        if (applicationService.isLoggedIn()) {

            if (navCount > 0) {
                for (item: Int in navCount downTo 0) {
                    navigation_view.removeHeaderView(navigation_view.getHeaderView(item))
                }
                navigation_view.menu.removeGroup(0)
            }

            navigation_view.inflateHeaderView(R.layout.navigation_drawer_header)
            navigation_view.inflateMenu(R.menu.navigation_drawer_menu)


            val navHeader = if (navigation_view.headerCount == 0) {
                navigation_view.getHeaderView(navigation_view.headerCount)
            }else {
                navigation_view.getHeaderView(navigation_view.headerCount - 1)
            }

            userViewModel.user.observe(this, Observer {

                if (navHeader != null && it != null) {
                    navHeader.nav_txt_user_name.text = "${it.name} ${it.family}"
                    navHeader.nav_txt_user_username.text = it.username
                    picasso.load(Config.SERVER_ADDRESS + "/" + it.image).placeholder(R.drawable.default_avatar)
                        .into(navHeader.nav_img_user_image)
                }

            })

        } else {

            if (navCount > 0) {

                for (item: Int in navCount downTo 0) {
                    navigation_view.removeHeaderView(navigation_view.getHeaderView(item))
                }
            }

            navigation_view.inflateHeaderView(R.layout.navigation_drawer_header_quest)
            navigation_view.menu.removeGroup(0)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun loginBtnOnClick(v: View) {
        navController.navigate(R.id.action_global_loginPageOneFragment)
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    override fun onDestroy() {
        userViewModel.deleteUser()
        super.onDestroy()
    }
}
