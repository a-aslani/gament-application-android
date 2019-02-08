package gamentorg.gament.ui.game


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gamentorg.gament.R
import dagger.android.support.AndroidSupportInjection
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.transition.Scene
import androidx.transition.TransitionManager
import gamentorg.gament.adapters.game.TournamentsListAdapter
import gamentorg.gament.db.entities.Tournament
import gamentorg.gament.vm.TournamentViewModel
import gamentorg.gament.vm.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_game.view.*
import javax.inject.Inject

class GameFragment : Fragment() {

    @Inject
    lateinit var tournamentListAdapter: TournamentsListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var tournamentViewModel: TournamentViewModel

    private var gameKey: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.tournament_rv
        rv.hasFixedSize()
        rv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rv.adapter = tournamentListAdapter

        tournamentListAdapter.onItemClickListener(object : TournamentsListAdapter.OnItemClickListener {
            override fun tournament(tournament: Tournament) {
                findNavController().navigate(R.id.action_gameFragment_to_tournamentFragment, bundleOf("tournament" to tournament))
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameKey = arguments!!.getInt("game_key")
        tournamentViewModel = ViewModelProviders.of(this, viewModelFactory).get(TournamentViewModel::class.java)
        tournamentViewModel.getAllTournamentsSortedByDate(gameKey.toString()).observe(this, Observer {
            tournamentListAdapter.submitList(it)
        })
    }

}
