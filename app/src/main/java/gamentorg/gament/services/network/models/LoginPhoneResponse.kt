package gamentorg.gament.services.network.models

import com.google.gson.annotations.SerializedName

data class LoginPhoneResponse(
    @SerializedName("data") var data: PhoneData,
    @SerializedName("state") var state: Boolean
)

data class PhoneData(
    @SerializedName("phone_key") var phoneKey: String
)