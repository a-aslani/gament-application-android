package gamentorg.gament.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import gamentorg.gament.db.entities.Tournament
import gamentorg.gament.repositories.TournamentPageKeyedRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TournamentViewModel @Inject constructor(private val repository: TournamentPageKeyedRepository): ViewModel() {

    fun getAllTournamentsSortedByDate(gameKey: String): LiveData<PagedList<Tournament>> {
        return repository.getAllTournamentsSortedByDate(gameKey)
    }
}