package gamentorg.gament.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import gamentorg.gament.db.entities.Rule
import gamentorg.gament.repositories.RuleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RuleViewModel @Inject constructor(private val repository: RuleRepository): ViewModel() {

    fun insertRule(key: String) {
        repository.insertRule(key)
    }

    fun getRuleByKey(key: String): LiveData<Rule> {
        return repository.getRuleByKey(key)
    }
}