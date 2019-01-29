package gamentorg.gament.ui.game


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import gamentorg.gament.R
import gamentorg.gament.constants.Config
import gamentorg.gament.db.entities.Tournament
import gamentorg.gament.utility.Tools
import gamentorg.gament.vm.GameViewModel
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

    private lateinit var tournament: Tournament

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
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

        tournament = arguments!!.getParcelable("tournament")!!

        gameViewModel.getGameByKey(tournament.gameKey.toString()).observe(this, Observer {

            picasso.load(Config.SERVER_ADDRESS + "/" + it.image).into(view.tournament_img_cover)
        })

        view.tournament_txt_description.text = tournament.name

        view.tournament_txt_quantity.text = "${tools.persianNumber(tournament.members.toString())}/${tools.persianNumber(tournament.quantity.toString())}"

        if (tournament.ticket != 0) {
            val ticket = tools.persianNumber(tools.numberFormat(tournament.ticket!!))
            view.tournament_txt_price.text = "$ticket تومان"
        } else {
            view.tournament_txt_price.text = "رایگان"
        }

        if (tournament.award != 0) {
            val gift = tools.persianNumber(tools.numberFormat(tournament.award!!))
            view.tournament_txt_gift.text = "$gift تومان"
        } else {
            view.tournament_txt_gift.text = "بدون جایزه"
        }

        if (tournament.state == 1) {
            view.tournament_txt_time_title.text = "تاریخ ایجاد"
            val date = tools.persianNumber(tournament.date!!)
            view.tournament_txt_time.text = date
        }

        when(tournament.type) {
            1 -> view.tournament_txt_mode.text = "گروهی"
            2 -> view.tournament_txt_mode.text = "گروهی حذفی"
            3 -> view.tournament_txt_mode.text = "حذفی"
            4 -> view.tournament_txt_mode.text = "انفرادی"
        }

        view.tournament_txt_count.text = "${tools.persianNumber(tournament.quantity.toString())} نفر"

        when(tournament.platform) {
            Config.PC -> view.tournament_txt_platform.text = "Computer"
            Config.PS -> view.tournament_txt_platform.text = "PlayStation 4"
            Config.XBOX -> view.tournament_txt_platform.text = "XBox 360"
            Config.MOBILE -> view.tournament_txt_platform.text = "Mobile"
        }
    }
}
