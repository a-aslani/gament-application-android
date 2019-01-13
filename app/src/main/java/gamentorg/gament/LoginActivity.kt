package gamentorg.gament

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.android.AndroidInjection

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == Config.PROFILE_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
//            val imageFile = File(data!!.data.path)
//            imageBody = RequestBody.create(MediaType.parse("image/*"), imageFile)
//        }
//    }

}
