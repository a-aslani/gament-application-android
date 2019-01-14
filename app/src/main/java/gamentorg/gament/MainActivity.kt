package gamentorg.gament

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import gamentorg.gament.constants.Config
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

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)

        //Set activity direction to RTL
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)

        setSupportActionBar(main_toolbar)

        setupToolbar()

        setupCollapsingToolbar()

        setupNavigationDrawer()


    }

    override fun onResume() {
        super.onResume()
        setupFab()
        setupNavigationDrawerData()
    }

    private fun setupFab() {

        if (!applicationService.isLoggedIn()) {
            main_fab.setImageResource(R.drawable.ic_person_add)
            main_fab.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
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
            ActionBarDrawerToggle(this, drawer_layout, main_toolbar, R.string.open_drawer, R.string.close_drawer)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        navigation_view.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.app_bar_message -> Toast.makeText(this, "message", Toast.LENGTH_SHORT).show()
            }

            drawer_layout.closeDrawer(GravityCompat.START, true)
            true
        }

    }

    fun logoutOnClick(v: View) {
        sharedPreferences.edit().remove(Config.API_TOKEN).apply()
        drawer_layout.closeDrawer(GravityCompat.START)
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

    private fun setupCollapsingToolbar() {

        main_collapsing_toolbar_layout.setCollapsedTitleTypeface(font)
        main_collapsing_toolbar_layout.setExpandedTitleTypeface(font)
    }

    private fun setupToolbar() {

        main_toolbar.setTitleTextColor(Color.WHITE)
        main_toolbar.setSubtitleTextColor(Color.WHITE)

        val navController = findNavController(R.id.main_fragment)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.main_fragment).navigateUp()

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_actions_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.app_bar_message -> Toast.makeText(this, "message", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    fun loginBtnOnClick(v: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    override fun onDestroy() {
        userViewModel.deleteUser()
        super.onDestroy()
    }
}
