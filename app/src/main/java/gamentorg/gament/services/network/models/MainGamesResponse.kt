package gamentorg.gament.services.network.models

import com.google.gson.annotations.SerializedName
import gamentorg.gament.db.entities.Game

data class MainGamesResponse(
    @SerializedName("data") var data: GamesData,
    @SerializedName("state") var state: Boolean
)

data class Data(
    @SerializedName("documents") var documents: List<Game>,
    @SerializedName("total_page") var totalPage: Int,
    @SerializedName("current_page") var currentPage: Int
)