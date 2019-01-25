package gamentorg.gament.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import dagger.android.support.AndroidSupportInjection
import gamentorg.gament.R
import gamentorg.gament.constants.Config
import gamentorg.gament.services.network.ApiRequest
import gamentorg.gament.services.network.ErrorHandler
import gamentorg.gament.services.network.models.CheckUsernameResponse
import gamentorg.gament.services.network.models.RegisterResponse
import gamentorg.gament.utility.Validator
import gamentorg.gament.vm.UserViewModel
import gamentorg.gament.vm.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_register.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject
import com.squareup.picasso.Picasso
import gamentorg.gament.ui.MainActivity
import gamentorg.gament.utility.FileUtils
import okhttp3.MultipartBody
import java.io.File


class RegisterFragment : Fragment() {

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

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var fileUtils: FileUtils

    private lateinit var userViewModel: UserViewModel

    private var imageMultiPart: MultipartBody.Part? = null

    private var btnClicked: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)

        (activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)

        register_progress_bar.visibility = View.INVISIBLE

        usernameCheck()

        getImage()

        if (!btnClicked) {
            createUser()
        }
    }

    private fun createUser() {
        register_btn_signup.setOnClickListener {

            val username = register_edt_username.text.toString().trim()
            val name = register_edt_name.text.toString().trim()
            val family = register_edt_family.text.toString().trim()

            val usernameError = validator.check(
                validator.isEmpty(getString(R.string.username), username),
                validator.minLength(getString(R.string.username), username, 4),
                validator.maxLength(getString(R.string.username), username, 20)
            )

            val nameError = validator.check(
                validator.isEmpty(getString(R.string.name), name),
                validator.maxLength(getString(R.string.name), name, 20)
            )

            val familyError = validator.check(
                validator.isEmpty(getString(R.string.family), family),
                validator.maxLength(getString(R.string.family), family, 20)
            )

            if (usernameError == "" && nameError == "" && familyError == "") {

                register_progress_bar.visibility = View.VISIBLE

                register_btn_signup.text = getString(R.string.sendig)

                btnClicked = true

                val usernameBody = RequestBody.create(MediaType.parse("text/plain"), username)
                val nameBody = RequestBody.create(MediaType.parse("text/plain"), name)
                val familyBody = RequestBody.create(MediaType.parse("text/plain"), family)

                apiRequest.createUser(
                    imageMultiPart,
                    usernameBody,
                    nameBody,
                    familyBody,
                    object : ApiRequest.Callback<RegisterResponse> {
                        override fun onReceive(response: Response<RegisterResponse>) {
                            if (response.isSuccessful) {
                                val apiToken = response.body()!!.data.apiToken
                                sharedPreferences.edit().putString(Config.API_TOKEN, apiToken).apply()
                                userViewModel.insertUser(apiToken)
                                findNavController().popBackStack()
                            }
                        }
                    })

            } else {
                if (usernameError != "") {
                    register_edt_username.error = usernameError
                }

                if (nameError != "") {
                    register_edt_name.error = nameError
                }

                if (familyError != "") {
                    register_edt_family.error = familyError
                }
            }
        }
    }

    private fun getImage() {

        register_img_profile_image.setOnClickListener {

            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"

            val pickIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/*"

            val chooserIntent = Intent.createChooser(getIntent, getString(R.string.choice_picture))
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            startActivityForResult(chooserIntent, Config.PROFILE_IMAGE_CODE)
        }
    }

    private fun usernameCheck() {
        register_edt_username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                apiRequest.checkUsername(s.toString(), object : ApiRequest.Callback<CheckUsernameResponse> {
                    override fun onReceive(response: Response<CheckUsernameResponse>) {
                        if (response.code() == 400) {
                            register_edt_username.error =
                                    errorHandler.badRequest(response.errorBody()?.string().toString())
                            return
                        }
                    }
                })
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        register_progress_bar.visibility = View.INVISIBLE

        if (requestCode == Config.PROFILE_IMAGE_CODE && resultCode == Activity.RESULT_OK) {

            if (data != null) {

                val imageFile = File(fileUtils.getRealPathFromURIPath(data.data!!, activity!!))

                val mFile = RequestBody.create(MediaType.parse("image/*"), imageFile)

                imageMultiPart = MultipartBody.Part.createFormData("image", imageFile.name, mFile)

                picasso.load(data.data).into(register_img_profile_image)
            }

        }
    }

    override fun onDestroy() {
        (activity as MainActivity).refreshUi()
        super.onDestroy()
    }

}
