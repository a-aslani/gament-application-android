package gamentorg.gament.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import gamentorg.gament.ui.GameActivity
import gamentorg.gament.R
import gamentorg.gament.adapters.main.GamesListAdapter
import gamentorg.gament.db.entities.Game
import gamentorg.gament.vm.GameViewModel
import gamentorg.gament.vm.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.view.*
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var gamesListAdapter: GamesListAdapter

    private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel = ViewModelProviders.of(this, viewModelFactory).get(GameViewModel::class.java)

        val rv = view.main_rv_games
        rv.layoutManager = LinearLayoutManager(activity!!.applicationContext, RecyclerView.VERTICAL, false)
        rv.hasFixedSize()
        rv.adapter = gamesListAdapter

        gamesListAdapter.onItemClickListener(object : GamesListAdapter.OnItemClickListener {

            override fun game(game: Game) {

                val intent = Intent(activity, GameActivity::class.java)

                intent.putExtra(getString(R.string.game_extras), game)
                startActivity(intent)
                activity!!.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out)
            }
        })

        gameViewModel.getAllGamesSortedByDate().observe(this, Observer {
            gamesListAdapter.submitList(it)
        })

    }
}
