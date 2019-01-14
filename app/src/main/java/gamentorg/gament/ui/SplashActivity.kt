package gamentorg.gament.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import gamentorg.gament.R
import gamentorg.gament.constants.Config
import gamentorg.gament.services.ApplicationService
import gamentorg.gament.vm.UserViewModel
import gamentorg.gament.vm.ViewModelFactory
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var applicationService: ApplicationService

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_splash)

        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)

        if (applicationService.isLoggedIn()) {
            userViewModel.insertUser(sharedPreferences.getString(Config.API_TOKEN, "")!!)
        }

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}
