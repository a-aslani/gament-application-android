package gamentorg.gament.adapters.game

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import gamentorg.gament.constants.Config
import gamentorg.gament.ui.game.GroupTournamentFragment
import gamentorg.gament.ui.game.IndividualMatchesFragment
import gamentorg.gament.ui.game.KnockoutTournamentFragment
import gamentorg.gament.ui.game.TeamKnockoutFragment

class TournamentsPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private val individualMatches: Int = 0
    private val teamKnockout: Int = 1
    private val knockoutTournament: Int = 2
    val groupTournament: Int = 3

    override fun getItem(position: Int): Fragment {

        return when(position) {
            individualMatches -> IndividualMatchesFragment()
            teamKnockout -> TeamKnockoutFragment()
            knockoutTournament -> KnockoutTournamentFragment()
            groupTournament -> GroupTournamentFragment()
            else -> GroupTournamentFragment()
        }
    }

    override fun getCount() = Config.GAME_TAB_COUNT

    override fun getPageTitle(position: Int): CharSequence? {

        return when(position) {
            individualMatches -> "انفرادی"
            teamKnockout -> "گروهی حذفی"
            knockoutTournament -> "حذفی"
            groupTournament -> "گروهی"
            else -> super.getPageTitle(position)
        }
    }
}