package gamentorg.gament.services.network

import android.content.SharedPreferences
import android.util.Log
import gamentorg.gament.constants.Config
import gamentorg.gament.services.network.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRequest @Inject constructor(private val apiService: ApiService, private val sharedPreferences: SharedPreferences) {

    private val networkError = "network error"

    interface Callback<T> {
        fun onReceive(response: Response<T>)
    }

    fun loginSendPhoneNumber(phone: String, callback: Callback<LoginPhoneResponse>) {

        apiService.sendPhoneNumber(phone).enqueue(object : retrofit2.Callback<LoginPhoneResponse> {
            override fun onFailure(call: Call<LoginPhoneResponse>, t: Throwable) {
                Log.e(networkError, t.message)
            }

            override fun onResponse(call: Call<LoginPhoneResponse>, response: Response<LoginPhoneResponse>) {
                callback.onReceive(response)
            }
        })
    }

    fun checkVerifyCode(verifyCode: String, phoneKey: String, callback: Callback<LoginVerifyCodeResponse>) {

        apiService.checkVerifyCode(verifyCode, phoneKey).enqueue(object : retrofit2.Callback<LoginVerifyCodeResponse> {
            override fun onFailure(call: Call<LoginVerifyCodeResponse>, t: Throwable) {
                Log.e(networkError, t.message)
            }

            override fun onResponse(call: Call<LoginVerifyCodeResponse>, response: Response<LoginVerifyCodeResponse>) {
                callback.onReceive(response)
            }
        })
    }

    fun checkUsername(username: String, callback: Callback<CheckUsernameResponse>) {

        apiService.checkUsername(username).enqueue(object : retrofit2.Callback<CheckUsernameResponse> {
            override fun onFailure(call: Call<CheckUsernameResponse>, t: Throwable) {
                Log.e(networkError, t.message)
            }

            override fun onResponse(call: Call<CheckUsernameResponse>, response: Response<CheckUsernameResponse>) {
                callback.onReceive(response)
            }
        })
    }

    fun createUser(image: MultipartBody.Part?, username: RequestBody, name: RequestBody, family: RequestBody, callback: Callback<RegisterResponse>) {

        val registerToken = sharedPreferences.getString(Config.REGISTER_TOKEN, "") ?: ""

        apiService.createUser(registerToken, image, username, name, family).enqueue(object : retrofit2.Callback<RegisterResponse> {
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e(networkError, t.message)
            }

            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                callback.onReceive(response)
            }
        })
    }

    fun fetchRule(key: String, callback: Callback<RuleResponse>) {

        apiService.fetchRule(key).enqueue(object : retrofit2.Callback<RuleResponse> {
            override fun onFailure(call: Call<RuleResponse>, t: Throwable) {
                Log.e(networkError, t.message)
            }

            override fun onResponse(call: Call<RuleResponse>, response: Response<RuleResponse>) {

                callback.onReceive(response)
            }
        })
    }
}