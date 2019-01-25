package gamentorg.gament.ui.login


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import dagger.android.support.AndroidSupportInjection
import gamentorg.gament.R
import gamentorg.gament.constants.Config
import gamentorg.gament.services.network.ApiRequest
import gamentorg.gament.services.network.ErrorHandler
import gamentorg.gament.services.network.models.LoginVerifyCodeResponse
import gamentorg.gament.utility.Validator
import gamentorg.gament.vm.UserViewModel
import gamentorg.gament.vm.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_login_page_two.*
import retrofit2.Response
import javax.inject.Inject
import android.widget.Toast
import gamentorg.gament.ui.MainActivity
import java.util.*
import kotlin.concurrent.timerTask


class LoginPageTwoFragment : Fragment() {

    @Inject
    lateinit var validator: Validator

    @Inject
    lateinit var apiRequest: ApiRequest

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var errorHandler: ErrorHandler

    private lateinit var userViewModel: UserViewModel

    private var btnClicked: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_page_two, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)

        if (!btnClicked) {
            sendVerifyCode()
        }

        setupTimer()
    }


    @SuppressLint("SetTextI18n")
    private fun setupTimer() {

        var time = 180.0

        if (time == 0.0) {

        } else {
            time--
            val min = Math.floor(time / 60)
            val sec = time - min * 60
            val minTxt = "0${min.toInt()}"
            var secTxt = sec.toInt().toString()
            if (sec < 10) {
                secTxt = "0$secTxt"
            }

            login_verify_code_timer.text = "$minTxt:$secTxt"
        }


    }

    private fun sendVerifyCode() {
        register_btn_send.setOnClickListener {

            val verifyCode = login_edt_verify_code.text.toString().trim()
            val error = validator.check(
                validator.isEmpty(getString(R.string.verify_code), verifyCode),
                validator.minLength(getString(R.string.verify_code), verifyCode, 5),
                validator.maxLength(getString(R.string.verify_code), verifyCode, 5)
            )

            if (error != "") {
                login_edt_verify_code.error = error
            } else {

                sendLoading(true)

                apiRequest.checkVerifyCode(verifyCode, LoginPageOneFragment.phoneKey!!, object :
                    ApiRequest.Callback<LoginVerifyCodeResponse> {

                    override fun onReceive(response: Response<LoginVerifyCodeResponse>) {

                        if (response.isSuccessful && response.body() != null) {
                            if (response.body()!!.state) {
                                val token = response.body()!!.data.token

                                if (response.body()!!.data.isNewUser) {
                                    sharedPreferences.edit().putString(Config.REGISTER_TOKEN, token).apply()
                                    findNavController().navigate(R.id.action_loginPageTwoFragment_to_registerFragment)

                                } else {
                                    sharedPreferences.edit().putString(Config.API_TOKEN, token).apply()
                                    userViewModel.insertUser(token)

                                    findNavController().popBackStack()
                                }
                            }
                        } else if (response.code() == 400) {
                            login_edt_verify_code.error =
                                    errorHandler.badRequest(response.errorBody()?.string().toString())
                            sendLoading(false)
                            return
                        }

                    }
                })
            }
        }
    }

    private fun sendLoading(loading: Boolean) {

        if (loading) {

            register_btn_send.text = getString(R.string.sendig)

            login_page_two_progress_bar.visibility = View.VISIBLE

            btnClicked = true

        } else {

            register_btn_send.text = getString(R.string.send)

            login_page_two_progress_bar.visibility = View.INVISIBLE

            btnClicked = false
        }
    }

    override fun onDestroy() {
        (activity as MainActivity).refreshUi()
        super.onDestroy()
    }

}
