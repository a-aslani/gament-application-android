package gamentorg.gament.ui.login


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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

        cancelLogin()

        sendVerifyCode()

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
                                    activity!!.finish()
                                }
                            }
                        } else if (response.code() == 400) {
                            login_edt_verify_code.error = errorHandler.badRequest(response.errorBody()?.string().toString())
                            return
                        }

                    }
                })
            }
        }
    }

    private fun cancelLogin() {
        register_btn_cancel.setOnClickListener {
            activity!!.finish()
        }
    }

}
