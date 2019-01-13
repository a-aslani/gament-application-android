package gamentorg.gament.ui.login


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.android.support.AndroidSupportInjection
import gamentorg.gament.R
import gamentorg.gament.services.network.ApiRequest
import gamentorg.gament.services.network.models.LoginPhoneResponse
import gamentorg.gament.utility.Validator
import kotlinx.android.synthetic.main.fragment_login_page_one.*
import retrofit2.Response
import javax.inject.Inject

class LoginPageOneFragment : Fragment() {

    @Inject
    lateinit var validator: Validator

    @Inject
    lateinit var apiRequest: ApiRequest

    companion object {
        var phoneKey: String? = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_page_one, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cancelLogin()

        sendPhoneNumber()
    }

    private fun sendPhoneNumber() {
        register_btn_next.setOnClickListener {

            val phoneNumber = login_txt_phone_number.text.toString().trim()

            val error = validator.check(
                validator.isFirstEquals(getString(R.string.phone_number), phoneNumber, "0"),
                validator.isEmpty(getString(R.string.phone_number), phoneNumber),
                validator.minLength(getString(R.string.phone_number), phoneNumber, 10),
                validator.maxLength(getString(R.string.phone_number), phoneNumber, 10)
            )

            if (error != "") {
                login_txt_phone_number.error = error
            } else {
                apiRequest.loginSendPhoneNumber(phoneNumber, object :
                    ApiRequest.Callback<LoginPhoneResponse> {

                    override fun onReceive(response: Response<LoginPhoneResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            if (response.body()!!.state) {
                                phoneKey = response.body()!!.data.phoneKey
                                findNavController().navigate(R.id.action_loginPageOneFragment_to_loginPageTwoFragment)
                            }
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
