package gamentorg.gament.ui.game


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gamentorg.gament.R
import gamentorg.gament.adapters.game.TournamentsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_game.view.*
import android.graphics.Typeface
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import dagger.android.support.AndroidSupportInjection
import gamentorg.gament.db.entities.Game
import javax.inject.Inject

class GameFragment : Fragment() {

    @Inject
    lateinit var font: Typeface

    private lateinit var tournamentsPagerAdapter: TournamentsPagerAdapter

    private lateinit var tabLayout: TabLayout

//    private var game: Game? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        game = arguments?.getParcelable(getString(R.string.game_extras))


        tabLayout = activity!!.game_tab_layout
        tournamentsPagerAdapter = TournamentsPagerAdapter(activity!!.supportFragmentManager)
        view.game_view_pager.adapter = tournamentsPagerAdapter
        view.game_view_pager.currentItem = tournamentsPagerAdapter.groupTournament

        tabLayout.setupWithViewPager(view.game_view_pager)

        changeTabsFont()

    }


    private fun changeTabsFont() {
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

}
