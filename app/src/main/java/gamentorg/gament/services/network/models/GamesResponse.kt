package gamentorg.gament.services.network.models

import com.google.gson.annotations.SerializedName
import gamentorg.gament.db.entities.Game

data class GamesResponse(
    @SerializedName("data") var data: GamesData,
    @SerializedName("state") var state: Boolean
)

data class GamesData(
    @SerializedName("documents") var documents: List<Game>,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("current_page") var currentPage: Int
)