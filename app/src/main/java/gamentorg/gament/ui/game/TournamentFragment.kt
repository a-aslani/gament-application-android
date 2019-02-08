package gamentorg.gament.ui.game


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import gamentorg.gament.R
import gamentorg.gament.constants.Config
import gamentorg.gament.db.entities.Tournament
import gamentorg.gament.utility.Tools
import gamentorg.gament.vm.GameViewModel
import gamentorg.gament.vm.RuleViewModel
import gamentorg.gament.vm.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_tournament.view.*
import javax.inject.Inject

class TournamentFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var tools: Tools

    lateinit var gameViewModel: GameViewModel

    lateinit var ruleViewModel: RuleViewModel

    private lateinit var tournament: Tournament

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tournament, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel = ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)
        ruleViewModel = ViewModelProviders.of(this, viewModelFactory).get(RuleViewModel::class.java)

        tournament = arguments!!.getParcelable("tournament")!!

        gameViewModel.getGameByKey(tournament.gameKey.toString()).observe(this, Observer {

            picasso.load(Config.SERVER_ADDRESS + "/" + it.image).into(view.tournament_img_cover)
        })

        view.tournament_txt_description.text = tournament.description

        view.tournament_txt_quantity.text =
            "${tools.persianNumber(tournament.members.toString())} نفر"

        if (tournament.ticket != 0) {
            val ticket = tools.persianNumber(tools.numberFormat(tournament.ticket!!))
            view.tournament_txt_price.text = "$ticket تومان"
        } else {
            view.tournament_txt_price.text = getString(R.string.free)
        }

        if (tournament.award != 0) {
            val gift = tools.persianNumber(tools.numberFormat(tournament.award!!))
            view.tournament_txt_gift.text = "$gift تومان"
        } else {
            view.tournament_txt_gift.text = getString(R.string.no_gift)
        }

        when (tournament.state) {
            1 -> view.tournament_txt_time_title.text = getString(R.string.created_at)
            2 -> {
                view.tournament_btn_join.text = "مشاهده ی نتایج"
                view.tournament_txt_time_title.text = getString(R.string.start_date)
            }
            3 -> {
                view.tournament_btn_join.text = "مشاهده ی نتایج"
                view.tournament_txt_time_title.text = getString(R.string.finish_date)
            }
        }

        view.tournament_txt_time.text = tools.persianNumber(tournament.date!!)

        when (tournament.type) {
            1 -> view.tournament_txt_mode.text = "گروهی"
            2 -> view.tournament_txt_mode.text = "گروهی حذفی"
            3 -> view.tournament_txt_mode.text = "حذفی"
            4 -> view.tournament_txt_mode.text = "انفرادی"
        }

        view.tournament_txt_count.text = "${tools.persianNumber(tournament.count.toString())} نفر"

        when (tournament.platform) {
            Config.PC -> view.tournament_txt_platform.text = getString(R.string.computer)
            Config.PS -> view.tournament_txt_platform.text = getString(R.string.playstation_4)
            Config.XBOX -> view.tournament_txt_platform.text = getString(R.string.xbox_360)
            Config.MOBILE -> view.tournament_txt_platform.text = getString(R.string.mobile)
        }

        view.tournament_txt_requirement.text = tournament.requirement

        // Insert tournament rule to local db
        ruleViewModel.insertRule(tournament.ruleKey!!)

        view.tournament_btn_rule.setOnClickListener {
            findNavController().navigate(R.id.action_tournamentFragment_to_ruleFragment, bundleOf("rule_key" to tournament.ruleKey))
        }

    }
}
