package gamentorg.gament.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gamentorg.gament.db.entities.Rule

@Dao
interface RuleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRule(rule: Rule)

    @Query("SELECT * FROM `rules` WHERE `key`==:key")
    fun getRuleByKey(key: String): LiveData<Rule>
}