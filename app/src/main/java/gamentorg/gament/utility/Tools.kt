package gamentorg.gament.utility

import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class Tools @Inject constructor(@Named("farsi") private val font: Typeface) {

    private val persianNumbers = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")

    fun changeTabsFont(tabLayout: TabLayout) {
        val vg = tabLayout.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildesCount = vgTab.childCount
            for (i in 0 until tabChildesCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    tabViewChild.typeface = font
                }
            }
        }
    }

    fun persianNumber(text: String): String {
        if (text.isEmpty()) {
            return ""
        }
        var out = ""
        val length = text.length
        for (i in 0 until length) {
            val c = text[i]
            when (c) {
                in '0'..'9' -> {
                    val number = Integer.parseInt(c.toString())
                    out += persianNumbers[number]
                }
                '٫' -> out += '،'.toString()
                else -> out += c
            }
        }
        return out
    }

    fun numberFormat(number: Int): String {
        val formatter = DecimalFormat("#,###,###")
        return formatter.format(number)
    }
}