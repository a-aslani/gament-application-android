package gamentorg.gament.ui.game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import gamentorg.gament.R
import gamentorg.gament.adapters.game.TournamentsPagerAdapter
import gamentorg.gament.constants.Config
import gamentorg.gament.db.entities.Game
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game.view.*
import javax.inject.Inject

class GameFragment : Fragment() {

    @Inject
    lateinit var picasso: Picasso

    private lateinit var tournamentsPagerAdapter: TournamentsPagerAdapter

    private lateinit var game: Game

    private lateinit var gameImageView: ImageView
    private lateinit var pcImageView: ImageView
    private lateinit var psImageView: ImageView
    private lateinit var xboxImageView: ImageView
    private lateinit var mobileImageView: ImageView
    private lateinit var gameTabLayout: TabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameImageView = activity!!.findViewById(R.id.game_banner_image)
        pcImageView = activity!!.findViewById(R.id.game_img_game_pc)
        psImageView = activity!!.findViewById(R.id.game_img_game_ps)
        xboxImageView = activity!!.findViewById(R.id.game_img_game_xbox)
        mobileImageView = activity!!.findViewById(R.id.game_img_game_mobile)
        gameTabLayout = activity!!.findViewById(R.id.game_tab_layout)

        game = activity!!.intent.getParcelableExtra(getString(R.string.game_extras))

        setupContentData()

        tournamentsPagerAdapter = TournamentsPagerAdapter(activity!!.supportFragmentManager)
        view.game_view_pager.adapter = tournamentsPagerAdapter
        view.game_view_pager.currentItem = tournamentsPagerAdapter.groupTournament

        gameTabLayout.setupWithViewPager(game_view_pager)

    }

    private fun setupContentData() {

        activity!!.title = game.name!!.toUpperCase()

        if (game.pc == true) {
            pcImageView.setImageResource(R.drawable.ic_computer_active)
        }
        if (game.ps == true) {
            psImageView.setImageResource(R.drawable.ic_ps_active)
        }
        if (game.mobile == true) {
            mobileImageView.setImageResource(R.drawable.ic_phone_active)
        }
        if (game.xbox == true) {
            xboxImageView.setImageResource(R.drawable.ic_xbox_active)
        }

        picasso.load(Config.SERVER_ADDRESS + "/" + game.image).into(gameImageView)
    }

}
