package gamentorg.gament.services.network.models

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("data") var data: RegisterData,
    @SerializedName("state") var state: Boolean
)

data class RegisterData(
    @SerializedName("token") var apiToken: String
)