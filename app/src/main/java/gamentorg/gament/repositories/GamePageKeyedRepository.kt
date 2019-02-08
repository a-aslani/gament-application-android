package gamentorg.gament.repositories

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import gamentorg.gament.db.AppDatabase
import gamentorg.gament.db.dao.GameDao
import gamentorg.gament.db.entities.Game
import gamentorg.gament.repositories.datasource.GameDataSourceFactory
import gamentorg.gament.services.ApplicationService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamePageKeyedRepository @Inject constructor(

    appDatabase: AppDatabase,
    private val gameDataSourceFactory: GameDataSourceFactory,
    private val applicationService: ApplicationService

) {

    private val gameDao: GameDao = appDatabase.gameDao()

    fun getAllGamesSortedByDate(): LiveData<PagedList<Game>> {
        val conf = PagedList.Config.Builder().setPageSize(5).build()
        return if (applicationService.hasInternetConnection()) {
            LivePagedListBuilder(gameDataSourceFactory, conf).build()
        } else {
            LivePagedListBuilder(gameDao.getAllGamesSortedByDate(), conf).build()
        }
    }
}