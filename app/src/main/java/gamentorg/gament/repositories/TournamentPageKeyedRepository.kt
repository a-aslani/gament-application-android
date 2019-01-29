package gamentorg.gament.repositories

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import gamentorg.gament.db.AppDatabase
import gamentorg.gament.db.dao.TournamentDao
import gamentorg.gament.db.entities.Tournament
import gamentorg.gament.repositories.datasource.TournamentDataSourceFactory
import gamentorg.gament.services.ApplicationService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TournamentPageKeyedRepository @Inject constructor(
    appDatabase: AppDatabase,
    private val tournamentDataSourceFactory: TournamentDataSourceFactory,
    private val applicationService: ApplicationService
) {

    private val tournamentDao: TournamentDao = appDatabase.tournamentDao()

    fun getAllTournamentsSortedByDate(gameKey: String): LiveData<PagedList<Tournament>> {
        val conf = PagedList.Config.Builder().setPageSize(5).setPrefetchDistance(8).build()
        return if (applicationService.hasInternetConnection()) {
            tournamentDataSourceFactory.gameKey(gameKey)
            LivePagedListBuilder(tournamentDataSourceFactory, conf).build()
        } else {
            LivePagedListBuilder(tournamentDao.getAllTournamentSortedByDate(gameKey), conf).build()
        }
    }
}