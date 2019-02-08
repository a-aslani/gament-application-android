package gamentorg.gament.services.network.models

import com.google.gson.annotations.SerializedName
import gamentorg.gament.db.entities.Rule

data class RuleResponse (
    @SerializedName("data") var data: RuleData,
    @SerializedName("state") var state: Boolean
)

data class RuleData (
    @SerializedName("document") var document: Rule
)