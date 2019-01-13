package gamentorg.gament.repositories.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import gamentorg.gament.db.entities.Game
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameDataSourceFactory @Inject constructor(private val gamePageKeyedDS: GamePageKeyedDS): DataSource.Factory<Int, Game>() {

    private val gamesLiveData: MutableLiveData<GamePageKeyedDS> = MutableLiveData()

    override fun create(): DataSource<Int, Game> {

        gamesLiveData.postValue(gamePageKeyedDS)

        return gamePageKeyedDS
    }
}