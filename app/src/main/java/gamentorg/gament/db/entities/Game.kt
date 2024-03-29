package gamentorg.gament.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "games")
data class Game(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "key") var key: Int,
    @ColumnInfo(name = "image") var image: String?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "pc") var pc: Boolean?,
    @ColumnInfo(name = "ps") var ps: Boolean?,
    @ColumnInfo(name = "xbox") var xbox: Boolean?,
    @ColumnInfo(name = "mobile") var mobile: Boolean?,
    @ColumnInfo(name = "web_banner") @SerializedName("web_banner") var webBanner: String?,
    @ColumnInfo(name = "mobile_banner") @SerializedName("mobile_banner") var mobileBanner: String?,
    @ColumnInfo(name = "created_at") @SerializedName("created_at") var createdAt: Int?,
    @ColumnInfo(name = "updated_at") @SerializedName("updated_at") var updatedAt: Int?
)