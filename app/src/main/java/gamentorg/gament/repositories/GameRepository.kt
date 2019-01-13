package gamentorg.gament.repositories

import android.os.AsyncTask
import gamentorg.gament.db.AppDatabase
import gamentorg.gament.db.dao.GameDao
import gamentorg.gament.db.entities.Game
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(appDatabase: AppDatabase) {

    private val gameDao: GameDao = appDatabase.gameDao()

    companion object {

        internal class InsertGames(private val dao: GameDao): AsyncTask<List<Game>, Void?, Void?>() {
            override fun doInBackground(vararg params: List<Game>?): Void? {
                dao.insertGames(params[0]!!)
                return null
            }
        }

    }

    fun insertGames(games: List<Game>) {
        InsertGames(gameDao).execute(games)
    }
}