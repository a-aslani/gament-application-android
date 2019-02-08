package gamentorg.gament.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rules")
data class Rule(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "key") var key: Int,
    @ColumnInfo(name = "description") var description: String?
)