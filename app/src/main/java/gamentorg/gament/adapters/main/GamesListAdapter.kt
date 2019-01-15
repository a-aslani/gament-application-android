package gamentorg.gament.adapters.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import gamentorg.gament.R
import gamentorg.gament.constants.Config
import gamentorg.gament.db.entities.Game
import kotlinx.android.synthetic.main.item_main_game_card.view.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesListAdapter @Inject constructor(private val picasso: Picasso) :
    PagedListAdapter<Game, GamesListAdapter.MainGamesListAdapterViewHolder>(object : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }) {

    private lateinit var onClickListener: OnItemClickListener

    fun onItemClickListener(onClickListener: OnItemClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainGamesListAdapterViewHolder {
        return MainGamesListAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_main_game_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainGamesListAdapterViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bindData(data, onClickListener)
        }
    }


    inner class MainGamesListAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(game: Game, onClickListener: OnItemClickListener) {

            picasso.load(Config.SERVER_ADDRESS + "/" + game.image).placeholder(R.drawable.placeholder_game_card).into(itemView.item_main_img_game_image)
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
                onClickListener.game(game)
            }
        }
    }

    interface OnItemClickListener {
        fun game(game: Game)
    }
}