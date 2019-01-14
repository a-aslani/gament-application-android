package gamentorg.gament.ui.game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import gamentorg.gament.R
import gamentorg.gament.constants.Config
import gamentorg.gament.db.entities.Game
import javax.inject.Inject

class GameFragment : Fragment() {

    @Inject
    lateinit var picasso: Picasso

    private lateinit var game: Game

    private lateinit var gameImageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameImageView = activity!!.findViewById(R.id.game_banner_image)

        game = activity!!.intent.getParcelableExtra(getString(R.string.game_extras))

        setupContentData()
    }

    private fun setupContentData() {

        activity!!.title = game.name!!.toUpperCase()

        picasso.load(Config.SERVER_ADDRESS + "/" + game.image).into(gameImageView)
    }

}
