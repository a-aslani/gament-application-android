package gamentorg.gament.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import gamentorg.gament.db.entities.Game
import gamentorg.gament.repositories.GamePageKeyedRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameViewModel @Inject constructor(private val pageKeyedRepository: GamePageKeyedRepository) : ViewModel() {

    fun getAllGamesSortedByDate(): LiveData<PagedList<Game>> {
        return pageKeyedRepository.getAllGamesSortedByDate()
    }
}