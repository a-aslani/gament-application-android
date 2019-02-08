package gamentorg.gament.adapters.game

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import gamentorg.gament.R
import gamentorg.gament.constants.Config
import gamentorg.gament.db.entities.Tournament
import gamentorg.gament.utility.Tools
import kotlinx.android.synthetic.main.item_game_tournament_card.view.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TournamentsListAdapter @Inject constructor(private val tools: Tools): PagedListAdapter<Tournament, TournamentsListAdapter.TournamentListViewHolder>(object : DiffUtil.ItemCallback<Tournament>(){
    override fun areItemsTheSame(oldItem: Tournament, newItem: Tournament): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: Tournament, newItem: Tournament): Boolean {
        return oldItem == newItem
    }
}) {

    private lateinit var onClickListener: OnItemClickListener

    fun onItemClickListener(onClickListener: OnItemClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentListViewHolder {
        return TournamentListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_game_tournament_card, parent, false))
    }

    override fun onBindViewHolder(holder: TournamentListViewHolder, position: Int) {

        val data = getItem(position)

        if (data != null) {
            holder.bindData(data, onClickListener)
        }
    }

    inner class TournamentListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindData(tournament: Tournament, onClickListener: OnItemClickListener) {

            itemView.item_tournament_txt_description.text = tools.persianNumber(tournament.description!!)
            itemView.item_tournament_txt_members.text = "${tools.persianNumber(tournament.members.toString())}/${tools.persianNumber(tournament.count.toString())}"
            itemView.item_tournament_txt_name.text = "رقابت ${tools.persianNumber(tournament.key.toString())}"

            if (tournament.ticket != 0) {
                itemView.item_tournament_txt_ticket.text = "${tools.persianNumber(tools.numberFormat(tournament.ticket!!))} تومان ورودی"
            } else {
                itemView.item_tournament_txt_ticket.text = "رایگان"
            }

            if (tournament.award != 0) {
                itemView.item_tournament_txt_award.text = "${tools.persianNumber(tools.numberFormat(tournament.award!!))} تومان جایزه"
            } else {
                itemView.item_tournament_txt_award.text = "بدون جایزه"
            }

            when (tournament.state) {
                1 -> itemView.item_tournament_txt_state.text = "ثبت نام"
                2 -> itemView.item_tournament_txt_state.text = "در حال برگذاری"
                3 -> itemView.item_tournament_txt_state.text = "پایان یافته"
            }

            when(tournament.platform) {
                Config.PC -> itemView.item_tournament_img_platform.setImageResource(R.drawable.ic_computer_active)
                Config.PS -> itemView.item_tournament_img_platform.setImageResource(R.drawable.ic_ps_active)
                Config.XBOX -> itemView.item_tournament_img_platform.setImageResource(R.drawable.ic_xbox_active)
                Config.MOBILE -> itemView.item_tournament_img_platform.setImageResource(R.drawable.ic_phone_active)
            }

            itemView.setOnClickListener {
                onClickListener.tournament(tournament)
            }
        }
    }

    interface OnItemClickListener {
        fun tournament(tournament: Tournament)
    }
}