package gamentorg.gament.adapters.game

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
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
            groupTournament -> GroupTournamentFragment()
            knockoutTournament -> KnockoutTournamentFragment()
            teamKnockout -> TeamKnockoutFragment()
            individualMatches -> IndividualMatchesFragment()
            else -> GroupTournamentFragment()
        }
    }

    override fun getCount() = Config.GAME_TAB_COUNT

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            groupTournament -> "گروهی"
            knockoutTournament -> "حذفی"
            teamKnockout -> "گروهی حذفی"
            individualMatches -> "انفرادی"
            else -> super.getPageTitle(position)
        }
    }
}