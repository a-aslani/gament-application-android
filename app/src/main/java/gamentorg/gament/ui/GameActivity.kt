package gamentorg.gament.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import dagger.android.AndroidInjection
import gamentorg.gament.R
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_game)

        //Set activity direction to RTL
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR

        setupToolbar()

    }

    private fun setupToolbar() {

        setSupportActionBar(game_toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        game_toolbar.setTitleTextColor(Color.WHITE)
        game_toolbar.setSubtitleTextColor(Color.WHITE)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
