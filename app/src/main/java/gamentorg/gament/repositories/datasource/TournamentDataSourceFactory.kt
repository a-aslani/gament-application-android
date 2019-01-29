package gamentorg.gament.repositories.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import gamentorg.gament.db.entities.Tournament
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TournamentDataSourceFactory @Inject constructor(private val tournamentPageKeyedDS: TournamentPageKeyedDS): DataSource.Factory<Int, Tournament>() {

    private val tournamentLiveData: MutableLiveData<TournamentPageKeyedDS> = MutableLiveData()
    private lateinit var game: String

    fun gameKey(key: String) {
        this.game = key
    }

    override fun create(): DataSource<Int, Tournament> {

        tournamentPageKeyedDS.gameKey(game)

        tournamentLiveData.postValue(tournamentPageKeyedDS)

        return tournamentPageKeyedDS
    }
}