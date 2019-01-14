package gamentorg.gament

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.android.AndroidInjection
import gamentorg.gament.services.ApplicationService
import kotlinx.android.synthetic.main.activity_game.*
import javax.inject.Inject

class GameActivity : AppCompatActivity() {

    @Inject
    lateinit var applicationService: ApplicationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_game)

        setupToolbar()

    }

    override fun onResume() {
        super.onResume()
        setupFab()
    }

    private fun setupToolbar() {

        setSupportActionBar(game_toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "CALL OF DUTY"

        game_toolbar.setTitleTextColor(Color.WHITE)
        game_toolbar.setSubtitleTextColor(Color.WHITE)

//        val navController = findNavController(R.id.game_toolbar)
//        setupActionBarWithNavController(navController)
    }

//    override fun onSupportNavigateUp() = findNavController(R.id.game_toolbar).navigateUp()

    private fun setupFab() {

        if (!applicationService.isLoggedIn()) {
            fab_controller.setImageResource(R.drawable.ic_person_add)
            fab_controller.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        } else {
            fab_controller.setImageResource(R.drawable.ic_game_pad)
            fab_controller.setOnClickListener {
                startActivity(Intent(this, ControllerActivity::class.java))
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}
