package gamentorg.gament.repositories

import android.os.AsyncTask
import gamentorg.gament.db.AppDatabase
import gamentorg.gament.db.dao.TournamentDao
import gamentorg.gament.db.entities.Tournament
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TournamentRepository @Inject constructor(appDatabase: AppDatabase) {

    private val tournamentDao: TournamentDao = appDatabase.tournamentDao()

    companion object {

        internal class InsertTournaments(val dao: TournamentDao): AsyncTask<List<Tournament>, Void?, Void?>() {
            override fun doInBackground(vararg params: List<Tournament>?): Void? {
                dao.insertTournaments(params[0]!!)
                return null
            }
        }
    }

    fun insertTournaments(tournaments: List<Tournament>) {
        InsertTournaments(tournamentDao).execute(tournaments)
    }
}