package gamentorg.gament.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import gamentorg.gament.R
import gamentorg.gament.constants.Config
import gamentorg.gament.db.entities.Game
import kotlinx.android.synthetic.main.item_main_rv_game_card.view.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainGamesListAdapter @Inject constructor(private val picasso: Picasso) :
    PagedListAdapter<Game, MainGamesListAdapter.MainGamesListAdapterViewHolder>(object : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainGamesListAdapterViewHolder {
        return MainGamesListAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_main_rv_game_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainGamesListAdapterViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bindData(data)
        }
    }


    inner class MainGamesListAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(game: Game) {

            picasso.load(Config.SERVER_ADDRESS + "/" + game.image).into(itemView.item_main_img_game_image)
            itemView.item_main_txt_game_name.text = game.name!!.toUpperCase()

            if (game.pc == true) {
                itemView.item_main_img_game_pc.setImageResource(R.drawable.ic_computer_active)
            }
            if (game.ps == true) {
                itemView.item_main_img_game_ps.setImageResource(R.drawable.ic_ps_active)
            }
            if (game.mobile == true) {
                itemView.item_main_img_game_mobile.setImageResource(R.drawable.ic_phone_active)
            }
            if (game.xbox == true) {
                itemView.item_main_img_game_xbox.setImageResource(R.drawable.ic_xbox_active)
            }

            itemView.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_gameActivity)
            }
        }
    }
}