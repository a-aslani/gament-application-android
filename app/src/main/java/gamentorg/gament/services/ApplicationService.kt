package gamentorg.gament.services

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import gamentorg.gament.constants.Config
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationService @Inject constructor(private val application: Application, private var sharedPreferences: SharedPreferences) {

    fun hasInternetConnection(): Boolean {
        val cm: ConnectivityManager = application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getString(Config.API_TOKEN, "") != ""
    }
}