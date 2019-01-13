package gamentorg.gament.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gamentorg.gament.db.entities.Game

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGames(games: List<Game>)

    @Query("SELECT * FROM games ORDER BY created_at DESC")
    fun getAllGamesSortedByDate(): DataSource.Factory<Int, Game>
}