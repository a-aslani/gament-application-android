package gamentorg.gament.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "key") var key: Int,
    @ColumnInfo(name = "image") var image: String?,
    @ColumnInfo(name = "username") var username: String?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "family") var family: String?,
    @ColumnInfo(name = "created_at") @SerializedName("created_at") var createdAt: Int?
)