package gamentorg.gament.db.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import gamentorg.gament.db.entities.Tournament

@Dao
interface TournamentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTournaments(tournaments: List<Tournament>)

    @Query("SELECT * FROM `tournaments` WHERE `game_key`==:gameKey ORDER BY `created_at` DESC")
    fun getAllTournamentSortedByDate(gameKey: String): DataSource.Factory<Int, Tournament>
}