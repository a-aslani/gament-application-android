package gamentorg.gament.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tournaments")
@Parcelize
data class Tournament(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "key") var key: Int,
    @ColumnInfo(name = "game_key") @SerializedName("game_key") var gameKey: String?,
    @ColumnInfo(name = "rule_key") @SerializedName("rule_key") var ruleKey: String?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "members") var members: Int?,
    @ColumnInfo(name = "award") var award: Int?,
    @ColumnInfo(name = "income") var income: Int?,
    @ColumnInfo(name = "percentage") var percentage: String?,
    @ColumnInfo(name = "platform") var platform: String?,
    @ColumnInfo(name = "quantity") var quantity: Int?,
    @ColumnInfo(name = "state") var state: Int?,
    @ColumnInfo(name = "sum") var sum: Int?,
    @ColumnInfo(name = "ticket") var ticket: Int?,
    @ColumnInfo(name = "type") var type: Int?,
    @ColumnInfo(name = "date") var date: String?,
    @ColumnInfo(name = "created_at") @SerializedName("created_at") var createdAt: Int?,
    @ColumnInfo(name = "updated_at") @SerializedName("updated_at") var updatedAt: Int?
) : Parcelable