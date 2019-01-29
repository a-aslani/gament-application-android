package gamentorg.gament.services.network.models

import com.google.gson.annotations.SerializedName
import gamentorg.gament.db.entities.Tournament

data class TournamentsResponse(
    @SerializedName("data") var data: TournamentsData,
    @SerializedName("state") var state: Boolean
)

data class TournamentsData(
    @SerializedName("documents") var documents: List<Tournament>,
    @SerializedName("total_pages") var totalPages: Int,
    @SerializedName("current_page") var currentPage: Int
)