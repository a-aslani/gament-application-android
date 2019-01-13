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

//    fun registerTimer() {
//                        var time = 180.0
//
//                if (time == 0.0) {
//
//                } else {
//                    time--
//                    var min = Math.floor(time/60)
//                    var sec = time - min * 60
//                    var minTxt = "0${min.toInt()}"
//                    var secTxt = sec.toInt().toString()
//                    if (sec < 10) {
//                        secTxt = "0$secTxt"
//                    }
//
//                    view.login_verify_code_timer.text = "$minTxt:$secTxt"
//
//                }
//    }
}