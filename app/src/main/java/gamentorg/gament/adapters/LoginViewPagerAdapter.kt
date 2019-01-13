package gamentorg.gament.adapters

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import gamentorg.gament.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginViewPagerAdapter @Inject constructor(private val application: Application): PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getCount() = 3

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(application.applicationContext)
        val view = when (position) {
            0 -> inflater.inflate(R.layout.item_login_vp_register, container, false)
            1 -> inflater.inflate(R.layout.item_login_vp_verify_code, container, false)
            else -> inflater.inflate(R.layout.item_login_vp_phone, container, false)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}