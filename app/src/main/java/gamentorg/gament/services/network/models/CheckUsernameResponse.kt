package gamentorg.gament.services.network.models

import com.google.gson.annotations.SerializedName

data class CheckUsernameResponse(
    @SerializedName("data") var data: CheckUsernameData,
    @SerializedName("state") var state: Boolean
)

data class CheckUsernameData(
    @SerializedName("message") var message: String
)