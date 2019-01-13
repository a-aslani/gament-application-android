package gamentorg.gament.services.network.models

import com.google.gson.annotations.SerializedName

data class LoginVerifyCodeResponse(
    @SerializedName("data") var data: CodeData,
    @SerializedName("state") var state: Boolean
)

data class CodeData(
    @SerializedName("token") var token: String,
    @SerializedName("is_new_user") var isNewUser: Boolean
)