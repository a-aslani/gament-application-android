package gamentorg.gament.services.network.models

import com.google.gson.annotations.SerializedName
import gamentorg.gament.db.entities.User

data class UserInfoResponse(
    @SerializedName("data") var data: UserInfoData,
    @SerializedName("state") var state: Boolean
)

data class UserInfoData (
    @SerializedName("document") var document: User
)
